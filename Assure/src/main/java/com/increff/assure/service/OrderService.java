package com.increff.assure.service;

import com.increff.assure.dao.OrderDao;
import com.increff.assure.dao.OrderItemDao;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.OrderPojo;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public OrderPojo createOrderUsingCSV(OrderPojo orderPojo, Map<String, Long> productsMap, Map<String,
            Long> productQuantitiesMap, List<String> clientSkuIds){
        OrderPojo orderPojoToReturn =  dao.insertAndReturnPojo(orderPojo);
        addOrderItems(orderPojoToReturn.getId(), productsMap, productQuantitiesMap, clientSkuIds);
        return orderPojoToReturn;
    }

    public OrderPojo addOrderUsingChannelSkuId(OrderPojo orderPojo, Map<String, Long> productsMap,
                                               Map<String, Long> productQuantitiesMap, List<String> channelSkuIds){
        orderPojo.setOrderStatus(OrderStatus.CREATED);
        OrderPojo orderPojoToReturn =  dao.insertAndReturnPojo(orderPojo);
        addOrderItems(orderPojoToReturn.getId(), productsMap, productQuantitiesMap, channelSkuIds);
        return orderPojoToReturn;
    }

    public OrderPojo getByChannelIdAndChannelOrderId(Long channelId, String channelOrderId){
        return dao.selectByChannel(channelId, channelOrderId);
    }

    public void changeStatusToAllocated(Long id){
        OrderPojo orderPojo = dao.select(id);
        orderPojo.setOrderStatus(OrderStatus.ALLOCATED);
    }

    public void addOrderItems(Long orderId, Map<String, Long> productsMap, Map<String, Long> productQuantitiesMap,
                              List<String> skuIds){
        for(String skuId: skuIds){
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setGlobalSkuId(productsMap.get(skuId));
            orderItemPojo.setOrderedQty(productQuantitiesMap.get(skuId));
            orderItemPojo.setAllocatedQty(0L);//qty integer
            orderItemPojo.setFulfilledQty(0L);
            orderItemPojo.setOrderId(orderId);
            orderItemDao.insert(orderItemPojo);
        }
    }

    public List<OrderItemPojo> getAllOrderItemsByOrderId(Long orderId){
        return orderItemDao.selectByOrderId(orderId);
    }

    public void allocateOrder(Long id, Long qtyToAllocate){
        OrderItemPojo orderItemPojo = orderItemDao.select(id);
        orderItemPojo.setAllocatedQty(orderItemPojo.getAllocatedQty()+qtyToAllocate);
    }

    public OrderPojo getCheckOrder(Long id) throws ApiException {
        OrderPojo orderPojo = dao.select(id);
        if(Objects.isNull(orderPojo)){
            throw new ApiException("Order doesn't exist with ID: "+id);
        }
        return orderPojo;
    }

}
