package com.increff.assure.controller;

import com.increff.assure.dto.OrderDto;
import com.increff.commons.Data.OrderData;
import com.increff.commons.Data.OrderItemData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderSearchForm;
import com.increff.commons.Form.OrderSearchFormChannelApp;
import com.increff.commons.Form.OrderWithChannelSkuIdForm;
import com.increff.commons.Form.OrderWithClientSkuIdForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Api
@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "uploads order")
    @RequestMapping(path = "/upload", method= RequestMethod.POST)
    public OrderData addOrderUsingClientSkuIds(@RequestBody OrderWithClientSkuIdForm orderWithClientSkuIdForm) throws ApiException {
        return dto.addOrderUsingClientSkuIds(orderWithClientSkuIdForm);
    }

    @ApiOperation(value = "add order by Channel Api")
    @RequestMapping(path = "", method= RequestMethod.POST)
    public OrderData addOrderUsingChannelSkuIds(@RequestBody OrderWithChannelSkuIdForm form) throws ApiException {
        return dto.addOrderUsingChannelSkuIds(form);
    }

    @ApiOperation(value = "allocates order")
    @RequestMapping(path = "/allocate", method= RequestMethod.PUT)
    public void allocateOrder(@RequestParam Long id) throws ApiException {
        dto.allocateOrder(id);
    }

    @ApiOperation(value = "Gets Order Items By ID")
    @RequestMapping(path = "/order-items/{id}", method= RequestMethod.GET)
    public List<OrderItemData> getOrderItems(@PathVariable Long id) throws ApiException {
        return dto.getOrderItems(id);
    }

    @ApiOperation(value = "search order by Order ID")
    @RequestMapping(path = "/search/{orderId}", method= RequestMethod.GET)
    public OrderData searchByOrderId(@PathVariable Long orderId) throws ApiException {
        return dto.searchByOrderId(orderId);
    }

    @ApiOperation(value = "search order")
    @RequestMapping(path = "/search", method= RequestMethod.POST)
    public List<OrderData> searchOrder(@RequestBody OrderSearchForm orderSearchForm) throws ApiException {
        System.out.println("'"+orderSearchForm.getEndDate()+"'");
        System.out.println("'"+orderSearchForm.getChannelOrderId()+"'");
        System.out.println("'"+orderSearchForm.getChannelId()+"'");
        return dto.searchOrder(orderSearchForm);
    }







}
