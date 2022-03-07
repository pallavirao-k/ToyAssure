package com.increff.assure.controller;

import com.increff.assure.dto.OrderDto;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.UploadOrderForm;
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
    @RequestMapping(path = "", method= RequestMethod.POST)
    public void addByUpload(@RequestBody UploadOrderForm uploadOrderForm) throws ApiException {
        dto.addByUpload(uploadOrderForm);
    }

    @ApiOperation(value = "allocates order")
    @RequestMapping(path = "", method= RequestMethod.PUT)
    public void allocateOrder(@RequestParam Long id){
        dto.allocateOrder(id);
    }


}
