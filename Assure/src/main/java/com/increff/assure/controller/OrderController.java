package com.increff.assure.controller;

import com.increff.assure.dto.OrderDto;
import com.increff.commons.Form.UploadOrderForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "Adds orders")
    @RequestMapping(path = "", method= RequestMethod.POST)
    public void add(@RequestBody UploadOrderForm uploadOrderForm){
        dto.add(uploadOrderForm);
    }
}
