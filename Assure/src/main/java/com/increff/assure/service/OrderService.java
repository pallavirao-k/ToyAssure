package com.increff.assure.service;

import com.increff.assure.dao.OrderDao;
import com.increff.assure.pojo.OrderPojo;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao dao;

    public OrderPojo addByUpload(OrderPojo orderPojo){
        return dao.insertAndReturnPojo(orderPojo);
    }

    public OrderPojo getByChannelIdAndChannelOrderId(Long channelId, String channelOrderId){
        return dao.selectByChannel(channelId, channelOrderId);
    }


}
