package com.increff.assure.controller;

import com.increff.assure.dto.ChannelDto;
import com.increff.commons.Data.ChannelData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ChannelForm;
import com.increff.commons.Form.UploadChannelListingForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api")
public class ChannelController {
    @Autowired
    private ChannelDto dto;

    @ApiOperation(value = "Adds a channel")
    @RequestMapping(path = "/channels", method = RequestMethod.POST)
    public ChannelData addChannel(@RequestBody ChannelForm form) throws ApiException {
        return dto.add(form);
    }

    @ApiOperation(value = "Gets a channel")
    @RequestMapping(path = "/channels/{id}", method = RequestMethod.GET)
    public ChannelData get(@PathVariable Long id) throws ApiException {
        return dto.getChannel(id);
    }

    @ApiOperation(value = "Gets a list of all channels")
    @RequestMapping(path = "/channels", method = RequestMethod.GET)
    public List<ChannelData> getAll() {
        return dto.getAllChannels();
    }



}
