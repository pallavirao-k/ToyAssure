package com.increff.assure.controller;

import com.increff.assure.dto.InvoiceDto;
import com.increff.assure.dto.OrderDto;
import com.increff.commons.Data.InvoiceResponse;
import com.increff.commons.Data.OrderData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderWithClientSkuIdForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceDto dto;

    @ApiOperation(value = "uploads order")
    @RequestMapping(path = "", method= RequestMethod.GET)
    public InvoiceResponse generateInvoice(@RequestParam Long orderId) throws Exception {
        return dto.generateInvoice(orderId);
    }

}
