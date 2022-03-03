package com.increff.assure.service;

import com.increff.assure.dao.ChannelListingDao;
import com.increff.assure.pojo.ChannelListingPojo;
import com.increff.commons.Data.ChannelListingData;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ChannelListingService {

    @Autowired
    private ChannelListingDao dao;

    public void add(List<ChannelListingPojo> pojoList){
        for(ChannelListingPojo pojo : pojoList){
            dao.insert(pojo);
        }
    }

    public ChannelListingPojo getById(Long id) throws ApiException {
        return getCheckId(id);
    }

    public List<ChannelListingPojo> getAll(){
        return dao.selectAll();
    }

    public ChannelListingPojo getCheckId(Long id) throws ApiException {
        ChannelListingPojo channelListingPojo = dao.select(id);
        if(Objects.isNull(channelListingPojo))throw new ApiException("Channel-Listing doesn't exist");
        return channelListingPojo;
    }
}
