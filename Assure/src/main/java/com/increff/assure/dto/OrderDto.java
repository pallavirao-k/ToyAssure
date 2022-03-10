package com.increff.assure.dto;

import com.increff.assure.pojo.*;
import com.increff.assure.service.*;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Data.ErrorData;
import com.increff.commons.Data.OrderData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderWithChannelSkuIdForm;
import com.increff.commons.Form.OrderItemWithChannelSkuId;
import com.increff.commons.Form.OrderItemWithClientSkuId;
import com.increff.commons.Form.OrderWithClientSkuIdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.increff.commons.Util.ConvertUtil.convert;
import static com.increff.commons.Util.ValidationUtil.checkDuplicates;
import static com.increff.commons.Util.ValidationUtil.validateEmptyFields;

@Service
public class OrderDto extends AbstractDto {

    @Autowired
    private OrderService service;
    @Autowired
    private PartyService partyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BinService binService;
    @Autowired
    private ChannelListingService channelListingService;


    public OrderData addOrderUsingClientSkuIds(OrderWithClientSkuIdForm orderWithClientSkuIdForm) throws ApiException{
        validateUploadOrderForm(orderWithClientSkuIdForm);
        checkClientIdAndCustomerId(orderWithClientSkuIdForm.getClientId(), orderWithClientSkuIdForm.getCustomerId());
        List<String> clientSkuIds = orderWithClientSkuIdForm.getFormList().stream().
                map(OrderItemWithClientSkuId::getClientSkuId).collect(Collectors.toList());
        Map<String, Long> productsMap= getCheckClientSkuId(orderWithClientSkuIdForm.getClientId(), clientSkuIds);
        Map<String, Long> productQuantitiesMap = orderWithClientSkuIdForm.getFormList().stream().collect(Collectors.
                toMap(value->value.getClientSkuId(), value->value.getQty()));

        OrderPojo orderPojo = convertUploadOrderForm(orderWithClientSkuIdForm);
        OrderPojo p = service.createOrderUsingCSV(orderPojo, productsMap, productQuantitiesMap, clientSkuIds);
        return convert(p, OrderData.class);
    }

    public OrderData addOrderUsingChannelSkuIds(OrderWithChannelSkuIdForm form) throws ApiException {
        validateOrderByChannelApiForm(form);
        checkClientIdAndCustomerId(form.getClientId(), form.getCustomerId());
        List<String> channelSkuIds = form.getOrderItemWithChannelSkuIdList().stream()
                .map(OrderItemWithChannelSkuId::getChannelSkuId).collect(Collectors.toList());
        Map<String, Long> productsMap = getCheckChannelSkuId(form.getClientId(),form.getChannelId(), channelSkuIds);
        Map<String, Long> productQuantitiesMap = form.getOrderItemWithChannelSkuIdList().stream().collect(Collectors.
                toMap(value->value.getChannelSkuId(), value->value.getQty()));

        OrderPojo orderPojo = convert(form, OrderPojo.class);
        OrderPojo p = service.addOrderUsingChannelSkuId(orderPojo, productsMap, productQuantitiesMap, channelSkuIds);
        return convert(p, OrderData.class);
    }


    @Transactional(rollbackOn = ApiException.class)
    public void allocateOrder(Long id) throws ApiException {
        OrderPojo orderPojo = service.getCheckOrder(id);
        if(orderPojo.getOrderStatus().equals(OrderStatus.CREATED)) {
            List<OrderItemPojo> orderItemPojoList = service.getAllOrderItemsByOrderId(id);
            for (OrderItemPojo orderItemPojo : orderItemPojoList) {
                Long qtyToAllocate = inventoryService.updateAvailableAndAllocatedQty(
                        orderItemPojo.getOrderedQty() - orderItemPojo.getAllocatedQty(),
                        orderItemPojo.getGlobalSkuId());
                InventoryPojo inventoryPojo = inventoryService.getCheckGlobalSkuId(orderItemPojo.getGlobalSkuId());
                service.allocateOrder(orderItemPojo.getId(), qtyToAllocate);
                binService.updateBinSkuQty(inventoryPojo.getAllocatedQty(), orderItemPojo.getGlobalSkuId());
            }
            checkAndAllocateOrder(id);
        }

    }

    public void checkAndAllocateOrder(Long id){
        List<OrderItemPojo> orderItemPojoList = service.getAllOrderItemsByOrderId(id);
        for(OrderItemPojo orderItemPojo:orderItemPojoList){
            if(orderItemPojo.getOrderedQty()!=orderItemPojo.getAllocatedQty()){
                return;
            }
        }
        service.changeStatusToAllocated(id);
    }


    private OrderPojo convertUploadOrderForm(OrderWithClientSkuIdForm orderWithClientSkuIdForm) throws ApiException {
        Long channelId = channelService.getCheckChannelByName("internal").getId();
        checkDuplicateChannelIdAndChannelOrderId(channelId, orderWithClientSkuIdForm.getChannelOrderId());
        OrderPojo orderPojo = convert(orderWithClientSkuIdForm, OrderPojo.class);
        orderPojo.setChannelId(channelId);
        orderPojo.setOrderStatus(OrderStatus.CREATED);
        return orderPojo;
    }

    private void validateChannelOrderId(String channelOrderId) throws ApiException {
        if(channelOrderId.trim().isEmpty()){
            throw new ApiException("Channel Order ID must not be empty");
        }
    }

    private void checkClientIdAndCustomerId(Long clientId, Long customerId) throws ApiException {
        partyService.getCheck(clientId);
        partyService.getCheck(customerId);
    }


    private Map<String, Long> getCheckClientSkuId(Long clientId, List<String> clientSkuIds) throws ApiException {
        List<ProductPojo> pojoList = productService.getByClientSkuIdAndClientIds(clientId, clientSkuIds);
        List<String> finalClientSkuIds = pojoList.stream().map(ProductPojo::getClientSkuId).collect(Collectors.toList());
        List<String> differences = clientSkuIds.stream().filter(element -> !finalClientSkuIds.contains(element))
                .collect(Collectors.toList());
        if(differences.size()>0){
            throw new ApiException(ErrorData.convert("Client SKU ID(s) don't exist", differences));
        }
        return  pojoList.stream().collect(Collectors.toMap(value ->value.getClientSkuId(), value->value.getGlobalSkuId()));

    }

    private Map<String, Long> getCheckChannelSkuId(Long clientId, Long channelId, List<String> channelSkuIds) throws ApiException {
        List<ChannelListingPojo> pojoList = channelListingService.getByClientIdChannelIdChannelSkuIds(clientId,
                channelId, channelSkuIds);
        List<String> finalChannelSkuIds = pojoList.stream().map(ChannelListingPojo::getChannelSkuId)
                .collect(Collectors.toList());
        List<String> differences = channelSkuIds.stream().filter(element -> !finalChannelSkuIds.contains(element))
                .collect(Collectors.toList());
        if(differences.size()>0){
            throw new ApiException(ErrorData.convert("Channel SKU ID(s) don't exist", differences));
        }
        return pojoList.stream().collect(Collectors.toMap(value ->value.getChannelSkuId(), value->value.getGlobalSkuId()));
    }

    private void validateUploadOrderForm(OrderWithClientSkuIdForm orderWithClientSkuIdForm) throws ApiException {
        formValidation(orderWithClientSkuIdForm);
        validateChannelOrderId(orderWithClientSkuIdForm.getChannelOrderId());
        List<String> clientSkuIds = orderWithClientSkuIdForm.getFormList().stream().map(OrderItemWithClientSkuId::getClientSkuId)
                .collect(Collectors.toList());
        validateEmptyFields(clientSkuIds, "Client SKU ID(s)");
        checkDuplicates(clientSkuIds, "Client SKU ID(s)");

    }

    private void validateOrderByChannelApiForm(OrderWithChannelSkuIdForm form) throws ApiException {
        formValidation(form);
        validateChannelOrderId(form.getChannelOrderId());
       List<String> channelSkuIds = form.getOrderItemWithChannelSkuIdList().stream().
               map(OrderItemWithChannelSkuId::getChannelSkuId).collect(Collectors.toList());
       validateEmptyFields(channelSkuIds, "Channel SKU ID(s)");
       checkDuplicates(channelSkuIds, "Channel SKU ID(s)");

    }

    private void checkDuplicateChannelIdAndChannelOrderId(Long channelId, String channelOrderId) throws ApiException {
        OrderPojo orderPojo = service.getByChannelIdAndChannelOrderId(channelId, channelOrderId);
        if(Objects.nonNull(orderPojo)){
            throw  new ApiException("Order already exists with channel ID: "+channelId+" and channel Order ID: "
                    +channelOrderId);
        }
    }




}



