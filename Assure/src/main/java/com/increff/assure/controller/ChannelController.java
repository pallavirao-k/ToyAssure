package com.increff.assure.controller;

import com.increff.assure.dto.ChannelDto;
import com.increff.commons.Constants.Invoice.*;
import com.increff.commons.Data.ChannelData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ChannelForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/channel")
public class ChannelController {
    @Autowired
    private ChannelDto dto;

    @ApiOperation(value = "Adds a channel")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody ChannelForm form) throws ApiException {
        dto.add(form);
    }

    @ApiOperation(value = "Gets a channel")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ChannelData get(@PathVariable Long id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets a list of all channels")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ChannelData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Gets a list of all channels")
    @RequestMapping(path = "/type", method = RequestMethod.GET)
    public List<ChannelData> getChannelsByInvoiceType(@RequestParam InvoiceType invoiceType) throws ApiException {
        return dto.getChannelsByInvoiceType(invoiceType);
    }
}
