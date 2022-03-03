package com.increff.assure.controller;

import com.increff.assure.dto.ChannelListingDto;
import com.increff.commons.Data.ChannelListingData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ChannelListingForm;
import com.increff.commons.Form.UploadChannelListingForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("api/channel-listings")
public class ChannelListingController {

    @Autowired
    private ChannelListingDto dto;

    @ApiOperation(value = "Adds channel-listings")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody UploadChannelListingForm form) throws ApiException {
        dto.add(form);
    }

    @ApiOperation(value = "Gets all channel-listings")
    @RequestMapping(path="", method = RequestMethod.GET)
    public List<ChannelListingData> getAll(){
        return dto.getAll();
    }

    @ApiOperation(value = "Gets a channel-listing bu id")
    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public ChannelListingData getById(@PathVariable Long id) throws ApiException {
        return dto.getById(id);
    }

    // scope for more......


}
