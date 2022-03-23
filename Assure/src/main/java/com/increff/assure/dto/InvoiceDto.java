package com.increff.assure.dto;

import com.increff.assure.pojo.*;
import com.increff.assure.service.*;
import com.increff.commons.Constants.Invoice;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Data.InvoiceData;
import com.increff.commons.Data.InvoiceResponse;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.increff.assure.util.InvoiceUtil.*;
import static com.increff.commons.Util.ConvertUtil.convert;
import static com.increff.commons.Util.XmlUtil.generatePdf;

@Service
public class InvoiceDto {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private InvoiceService service;


    //TODO change it based on invoice type
    @Transactional(rollbackOn = ApiException.class)
    public InvoiceResponse generateInvoice(Long orderId) throws Exception {
        OrderPojo orderPojo = orderService.getCheckOrder(orderId);
        ChannelPojo channelPojo = channelService.getCheckChannelById(orderPojo.getChannelId());

        if (orderPojo.getOrderStatus().equals(OrderStatus.CREATED)) {
            throw new ApiException("Order is not allocated");
        }
        if (orderPojo.getOrderStatus().equals(OrderStatus.FULFILLED)) {
            return convertToInvoiceResponse(service.getcheckByOrderId(orderId));
        }
        if (channelPojo.getInvoiceType().equals(Invoice.InvoiceType.SELF)) {
            System.out.println("internallllll");
                String invoiceUrl = generatePdf(getInvoiceData(orderId));
                InvoicePojo invoicePojo = convertToInvoicePojo(orderId, invoiceUrl);
                return convertToInvoiceResponse(service.add(invoicePojo));
        }
        return generateInvoiceInChannelApp(getInvoiceData(orderId));

        }

        @Transactional(rollbackOn = ApiException.class)
        private InvoiceData getInvoiceData(Long orderId) throws Exception {
            List<OrderItemPojo> pojos = orderService.getItemsByOrderId(orderId);
            Map<Long, Long> globalSkuIdsToQty = pojos.stream().collect(Collectors.
                    toMap(val->val.getGlobalSkuId(), val->val.getOrderedQty()));

            inventoryService.updateFulfilledQty(globalSkuIdsToQty);
            orderService.updateFulfilledQty(orderId);

            List<Long> globalSkuIds = pojos.stream().
                    map(OrderItemPojo::getGlobalSkuId).collect(Collectors.toList());
            Map<Long, ProductPojo> globalSkuToProduct = productService.getByGlobalSkuIds(globalSkuIds);
            return convertToInvoiceData(pojos, globalSkuToProduct);
        }

    public InvoiceResponse generateInvoiceInChannelApp(InvoiceData invoiceData){
        System.out.println("channel-app");
        String url = restTemplate.postForObject("http://localhost:9000/Channel-App/api/orders/invoice",
                invoiceData, String.class);
        InvoicePojo invoicePojo = convertToInvoicePojo(invoiceData.getOrderId(), url);
        return convertToInvoiceResponse(service.add(invoicePojo));
    }




    }






