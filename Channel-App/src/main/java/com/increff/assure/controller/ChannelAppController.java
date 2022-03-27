package com.increff.assure.controller;

import com.increff.assure.dto.ChannelAppDto;
import com.increff.commons.Data.InvoiceData;
import com.increff.commons.Data.InvoiceResponse;
import com.increff.commons.Data.OrderData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderSearchForm;
import com.increff.commons.Form.OrderSearchFormChannelApp;
import com.increff.commons.Form.OrderWithChannelSkuIdForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/orders")
public class ChannelAppController {

    @Autowired
    private ChannelAppDto dto;

    @ApiOperation(value = "add order")
    @RequestMapping(path = "", method= RequestMethod.POST)
    public void addOrder(@RequestBody OrderWithChannelSkuIdForm form) throws IOException, ApiException {
        dto.placeOrder(form);
    }


    @ApiOperation(value = "generate invoice")
    @RequestMapping(path = "/invoice", method= RequestMethod.POST)
    public String generateInvoice(@RequestBody InvoiceData invoiceData) throws Exception {
        return dto.generateInvoice(invoiceData);
    }

    @ApiOperation(value = "search order")
    @RequestMapping(path = "/search", method= RequestMethod.POST)
    public List<OrderData> searchOrder(@RequestBody OrderSearchForm form){
        return dto.searchOrder(form);
    }


}
