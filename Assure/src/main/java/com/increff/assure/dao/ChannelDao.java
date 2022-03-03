package com.increff.assure.dao;

import com.increff.assure.pojo.ChannelPojo;
import com.increff.commons.Constants.Invoice.*;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ChannelDao extends GenericDao<ChannelPojo> {

    private static final String SELECT_BY_NAME = "select p from ChannelPojo p where channelName=:name";
    private static final String SELECT_CHANNELS_BY_INVOICE_TYPE = "select p from ChannelPojo p where invoiceType=:type";


    public ChannelPojo selectByName(String name){
        ChannelPojo p;
        try {
            TypedQuery<ChannelPojo> q = getQuery(SELECT_BY_NAME, ChannelPojo.class);
            q.setParameter("name", name);
            p= q.getSingleResult();
        }catch(NoResultException e){
            p=null;
        }
        return p;
    }

    public List<ChannelPojo> selectChannelsByInvoiceType(InvoiceType invoiceType){
        TypedQuery<ChannelPojo> q = getQuery(SELECT_CHANNELS_BY_INVOICE_TYPE, ChannelPojo.class);
        q.setParameter("type", invoiceType);
        return q.getResultList();
    }
}
