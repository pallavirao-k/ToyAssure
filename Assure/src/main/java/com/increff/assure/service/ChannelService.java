package com.increff.assure.service;

import com.increff.assure.dao.ChannelDao;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.commons.Constants.Invoice.*;
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

    public void add(ChannelPojo p) throws ApiException {

        ChannelPojo ex = dao.selectByName(p.getChannelName());
        isNotNull(ex, "Channel with name "+p.getChannelName()+" already exists");
        dao.insert(p);
    }

    public ChannelPojo get(Long id) throws ApiException {
        return getCheckId(id);
    }

    public List<ChannelPojo> getAll(){
        return dao.selectAll();
    }

    public List<ChannelPojo> getChannelsByInvoiceType(InvoiceType invoiceType){
        return dao.selectChannelsByInvoiceType(invoiceType);
    }

    public ChannelPojo getCheckId(Long id) throws ApiException {
        ChannelPojo cp = dao.select(id);
        if(Objects.isNull(cp)) throw new ApiException("Channel doesn't exist with id: "+id);
        return cp;
    }

    public ChannelPojo getCheckByName(String name) throws ApiException {
        ChannelPojo cp = dao.selectByName(name);
        if(Objects.isNull(cp))throw new ApiException("Channel with name: "+name+" doesn't exist");
        return cp;
    }
}
