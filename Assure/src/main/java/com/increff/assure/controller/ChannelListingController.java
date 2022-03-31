package com.increff.assure.controller;

import com.increff.assure.dto.ChannelListingDto;
import com.increff.commons.Data.ChannelListingData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.UploadChannelListingForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/channel-listings")
public class ChannelListingController {
    @Autowired
    private ChannelListingDto dto;

    @ApiOperation(value = "Adds channel-listings")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public List<ChannelListingData> addChannelListings(@RequestBody UploadChannelListingForm form) throws ApiException {

        return dto.addChannelListings(form);
    }
}
