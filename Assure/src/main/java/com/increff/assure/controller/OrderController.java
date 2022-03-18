package com.increff.assure.controller;

import com.increff.assure.dto.OrderDto;
import com.increff.commons.Data.OrderData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderWithChannelSkuIdForm;
import com.increff.commons.Form.OrderWithClientSkuIdForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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




}
