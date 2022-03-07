package com.increff.assure.dto;

import com.increff.assure.pojo.InventoryPojo;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.*;
import com.increff.commons.Data.ErrorData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderForm;
import com.increff.commons.Form.UploadOrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.increff.commons.Util.ConvertUtil.convert;

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
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BinService binService;

    public void addByUpload(UploadOrderForm uploadOrderForm) throws ApiException {
        validationsAndChecks(uploadOrderForm);
        OrderPojo orderPojo = convertUploadOrderForm(uploadOrderForm);
        OrderPojo p = service.addByUpload(orderPojo);
        List<String> initialClientSkuIds = uploadOrderForm.getFormList().stream().map(OrderForm::getClientSkuId).collect(Collectors.toList());
        List<Long> quantites = uploadOrderForm.getFormList().stream().map(OrderForm::getQty).collect(Collectors.toList());
        uploadOrderItems(p.getId(), uploadOrderForm.getClientId(),initialClientSkuIds, quantites);
    }



    public void allocateOrder(Long id){
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllByOrderId(id);
        for(OrderItemPojo orderItemPojo : orderItemPojoList){
            inventoryService.updateAvailableAndAllocatedQty(orderItemPojo.getOrderedQty(), orderItemPojo.getGlobalSkuId());
            InventoryPojo inventoryPojo = inventoryService.get(orderItemPojo.getGlobalSkuId());
            binService.updateBinSKuQty(inventoryPojo.getAllocatedQty(), orderItemPojo.getGlobalSkuId());
        }
        //checkForAllocation();
    }


    public void uploadOrderItems(Long orderId, Long clientId, List<String> initialClientSkuIds, List<Long> quantities){
        List<ProductPojo> pojoList = productService.getByClientSkuIdAndClientIds(clientId, initialClientSkuIds);
        List<Long> globalSkuIds = pojoList.stream().map(ProductPojo::getGlobalSkuId).collect(Collectors.toList());
        orderItemService.addOrderItems(orderId, globalSkuIds, quantities);
    }

    private OrderPojo convertUploadOrderForm(UploadOrderForm uploadOrderForm) throws ApiException {
        OrderPojo orderPojo = convert(uploadOrderForm, OrderPojo.class);
        orderPojo.setChannelId(channelService.getCheckChannelByName("internal").getId());
        checkDuplicateChannelIdAndChannelOrderId(orderPojo.getChannelId(),orderPojo.getChannelOrderId());
        return orderPojo;
    }

    public void validateChannelOrderId(String channelOrderId) throws ApiException {
        if(channelOrderId.trim().isEmpty()){
            throw new ApiException("Channel Order ID must not be blank");
        }
    }

    private void checkClientIdAndCustomerId(UploadOrderForm uploadOrderForm) throws ApiException {
        partyService.getCheck(uploadOrderForm.getClientId());
        partyService.getCheck(uploadOrderForm.getCustomerId());
    }

    private void validateClientSkuId(List<OrderForm> formList) throws ApiException {
        Long index=1L;
        List<Long> indexes = new ArrayList<>();
        Set<String> clientSkuIds = new HashSet<>();
        List<Long> indexes2 = new ArrayList<>();
        for(OrderForm form: formList){
            if(form.getClientSkuId().trim().isEmpty()){
                indexes.add(index);
            }
            else{
                if(!clientSkuIds.contains(form.getClientSkuId())){
                    clientSkuIds.add(form.getClientSkuId());
                }
                else{
                    indexes2.add(index);
                }
            }
            index++;
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert("Client SKU ID(s) at these indexes are blank", indexes));
        }
        if(indexes2.size()>0){
            throw new ApiException(ErrorData.convert("Client SKU ID(s) at these indexes are being repeated", indexes2));
        }

    }

    private void checkClientSkuId(Long clientId, List<OrderForm> formList) throws ApiException {

        List<String> initialClientSkuIds = formList.stream().map(OrderForm::getClientSkuId).collect(Collectors.toList());
        List<ProductPojo> pojoList = productService.getByClientSkuIdAndClientIds(clientId, initialClientSkuIds);
        List<String> finalClientSkuIds = pojoList.stream().map(ProductPojo::getClientSkuId).collect(Collectors.toList());
        List<String> differences = initialClientSkuIds.stream().filter(element -> !finalClientSkuIds.contains(element)).collect(Collectors.toList());

        if(differences.size()>0){
            throw new ApiException(ErrorData.convert("These Client SKU ID(s) don't exist", differences));
        }

    }
    private void validateAndCheckClientSkuId(Long clientId, List<OrderForm> formList) throws ApiException{
        validateClientSkuId(formList);
        checkClientSkuId(clientId, formList);
    }

    private void validationsAndChecks(UploadOrderForm uploadOrderForm) throws ApiException {
        formValidation(uploadOrderForm);
        validateChannelOrderId(uploadOrderForm.getChannelOrderId());
        checkClientIdAndCustomerId(uploadOrderForm);
        validateAndCheckClientSkuId(uploadOrderForm.getClientId(), uploadOrderForm.getFormList());
    }

    private void checkDuplicateChannelIdAndChannelOrderId(Long channelId, String channelOrderId) throws ApiException {
        OrderPojo orderPojo = service.getByChannelIdAndChannelOrderId(channelId, channelOrderId);
        if(Objects.nonNull(orderPojo)){
            throw  new ApiException("Order with channel Id: "+channelId+" and channel Order Id: "+channelOrderId+" already exists");
        }
    }


}
