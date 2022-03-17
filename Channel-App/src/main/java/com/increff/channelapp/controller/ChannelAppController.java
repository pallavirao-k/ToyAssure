package com.increff.channelapp.controller;

import com.increff.channelapp.dto.ChannelAppDto;
import com.increff.commons.Data.OrderData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderWithChannelSkuIdForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api
@RestController
@RequestMapping("/api/channel-app/orders")
public class ChannelAppController {

    @Autowired
    private ChannelAppDto dto;

    @ApiOperation(value = "add order by Channel Api")
    @RequestMapping(path = "", method= RequestMethod.POST)
    public void addOrder(@RequestBody OrderWithChannelSkuIdForm form) throws IOException {
        dto.addOrder(form);
    }

}
