package com.increff.assure.service;

import com.increff.assure.dao.OrderItemDao;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;

    public void addOrderItems(Long orderId, List<Long> globalSkuIds, List<Long> quantities ){
        for(int i = 0; i<globalSkuIds.size();i++){
            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setGlobalSkuId(globalSkuIds.get(i));
            orderItemPojo.setOrderedQty(quantities.get(i));
            orderItemPojo.setOrderId(orderId);
            dao.insert(orderItemPojo);
        }
    }

    public List<OrderItemPojo> getAllByOrderId(Long orderId){
        return dao.selectByOrderId(orderId);
    }

}
