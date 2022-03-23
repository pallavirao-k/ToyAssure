package com.increff.assure.service;

import com.increff.assure.dao.OrderDao;
import com.increff.assure.dao.OrderItemDao;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.assure.spring.OrderSearchProperties;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderItemDao orderItemDao;

    public OrderPojo addOrder(OrderPojo orderPojo){
        OrderPojo orderPojoToReturn =  dao.insertAndReturnPojo(orderPojo);
        return orderPojoToReturn;
    }

    public void insertOrderItem(OrderItemPojo orderItemPojo){
        orderItemDao.insert(orderItemPojo);
    }


    public OrderPojo getByChannelIdAndChannelOrderId(Long channelId, String channelOrderId){
        return dao.selectByChannel(channelId, channelOrderId);
    }

    public void changeStatusToAllocated(Long id){
        OrderPojo orderPojo = dao.select(id);
        orderPojo.setOrderStatus(OrderStatus.ALLOCATED);
    }

    public List<OrderItemPojo> getItemsByOrderId(Long orderId){
        return orderItemDao.selectByOrderId(orderId);
    }

    public void allocateOrder(Long id, Long qtyToAllocate){
        OrderItemPojo orderItemPojo = orderItemDao.select(id);
        orderItemPojo.setAllocatedQty(orderItemPojo.getAllocatedQty()+qtyToAllocate);
    }

    public void updateFulfilledQty(Long orderId){
        List<OrderItemPojo> list = orderItemDao.selectByOrderId(orderId);
        for(OrderItemPojo orderItemPojo: list){
            orderItemPojo.setAllocatedQty(orderItemPojo.getAllocatedQty()-orderItemPojo.getOrderedQty());
            orderItemPojo.setFulfilledQty(orderItemPojo.getFulfilledQty()+orderItemPojo.getOrderedQty());
        }
        OrderPojo orderPojo = dao.select(orderId);
        orderPojo.setOrderStatus(OrderStatus.FULFILLED);
    }

    public List<OrderPojo> searchOrder(OrderSearchProperties properties){
        return dao.selectByProperties(properties);
    }

    public OrderPojo getCheckOrder(Long id) throws ApiException {
        OrderPojo orderPojo = dao.select(id);
        if(Objects.isNull(orderPojo)){
            throw new ApiException("Order doesn't exist with ID: "+id);
        }
        return orderPojo;
    }

}
