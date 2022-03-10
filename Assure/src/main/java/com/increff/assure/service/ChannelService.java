package com.increff.assure.service;

import com.increff.assure.dao.ChannelDao;
import com.increff.assure.dao.ChannelListingDao;
import com.increff.assure.pojo.ChannelListingPojo;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static com.increff.assure.util.NormalizeUtil.normalize;


@Service
@Transactional(rollbackOn = ApiException.class)
public class ChannelService extends AbstarctService {
    @Autowired
    private ChannelDao dao;
    @Autowired
    private ChannelListingDao channelListingDao;

    public ChannelPojo addChannel(ChannelPojo p) throws ApiException {
        normalize(p);
        ChannelPojo exists = dao.selectByName(p.getChannelName());
        if(Objects.nonNull(exists)) {
            return exists;
        }
        dao.insert(p);
        return p;
    }

    public ChannelPojo getChannel(Long id) throws ApiException {
        return getCheckChannelById(id);
    }

    public List<ChannelPojo> getAllChannels(){
        return dao.selectAll();
    }


    public ChannelPojo getCheckChannelById(Long id) throws ApiException {
        ChannelPojo cp = dao.select(id);
        if(Objects.isNull(cp)) throw new ApiException("Channel doesn't exist with id: "+id);
        return cp;
    }

    public ChannelPojo getCheckChannelByName(String name) throws ApiException {
        ChannelPojo cp = dao.selectByName(name);
        if(Objects.isNull(cp))throw new ApiException("Channel with name: "+name+" doesn't exist");
        return cp;
    }



}
