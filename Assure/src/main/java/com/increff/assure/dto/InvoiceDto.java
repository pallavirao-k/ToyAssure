package com.increff.assure.dto;

import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.ChannelService;
import com.increff.assure.service.InventoryService;
import com.increff.assure.service.OrderService;
import com.increff.assure.service.ProductService;
import com.increff.commons.Constants.Invoice;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Data.InvoiceData;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.increff.assure.util.InvoiceUtil.*;
import static com.increff.commons.Util.ConvertUtil.convert;

@Service
public class InvoiceDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ChannelService channelService;


    //TODO change it based on invoice type
    @Transactional(rollbackOn = ApiException.class)
    public void generateInvoice(Long orderId) throws Exception {
        OrderPojo orderPojo = orderService.getCheckOrder(orderId);
        ChannelPojo channelPojo = channelService.getCheckChannelById(orderPojo.getChannelId());

        if (orderPojo.getOrderStatus().equals(OrderStatus.CREATED)) {
            return;
        } else if (orderPojo.getOrderStatus().equals(OrderStatus.FULFILLED)) {
            getInvoice(orderId);
        } else {
            if (channelPojo.getInvoiceType().equals(Invoice.InvoiceType.SELF)) {

                List<OrderItemPojo> pojos = orderService.getItemsByOrderId(orderId);
                inventoryService.updateFulfilledQty(pojos);
                orderService.updateFulfilledQty(pojos);
                List<OrderItemPojo> list = orderService.getItemsByOrderId(orderId);
                List<Long> globalSkuIds = list.stream().
                        map(OrderItemPojo::getGlobalSkuId).collect(Collectors.toList());
                Map<Long, ProductPojo> globalSkuToProduct = productService.getByGlobalSkuIds(globalSkuIds);

                InvoiceData invoiceData = convertToInvoiceData(list, globalSkuToProduct);
                generatePdf(invoiceData);
            }
            else{

            }
        }


    }





}
