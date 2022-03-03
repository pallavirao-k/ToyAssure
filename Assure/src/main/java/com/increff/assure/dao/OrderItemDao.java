package com.increff.assure.dao;

import com.increff.assure.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends GenericDao<OrderItemPojo>{


    private static String SELECT_BY_ORDER_ID_GLOBAL_SKU_ID = "select p from OrderItemPojo p where orderId=:orderId AND globalSkuId=:globalSkuId";
    private static String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where orderId=:orderId";

    public OrderItemPojo selectByOrderIdAndGlobalSkuId(Long orderId, Long globalSkuId){
        TypedQuery<OrderItemPojo> q = getQuery(SELECT_BY_ORDER_ID_GLOBAL_SKU_ID, OrderItemPojo.class);
        q.setParameter("orderId", orderId);
        q.setParameter("globalSkuId", globalSkuId);
        return q.getSingleResult();
    }
    public  List<OrderItemPojo> selectByOrderId(Long orderId){
        TypedQuery<OrderItemPojo> q = getQuery(SELECT_BY_ORDER_ID, OrderItemPojo.class);
        q.setParameter("orderId", orderId);
        return q.getResultList();

    }

}
