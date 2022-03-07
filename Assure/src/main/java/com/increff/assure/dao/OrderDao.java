package com.increff.assure.dao;

import com.increff.assure.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class OrderDao extends GenericDao<OrderPojo> {

    private static String SELECT_BY_CHANNEL_ID_CHANNEL_ORDER_ID = "select p from OrderPojo p where channelId=:channelId AND channelOrderId=:channelOrderId";


    public OrderPojo insertAndReturnPojo(OrderPojo orderPojo){
        em.persist(orderPojo);
        return orderPojo;
    }

    public OrderPojo selectByChannel(Long channelId, String channelOrderId){
        TypedQuery<OrderPojo> q = getQuery(SELECT_BY_CHANNEL_ID_CHANNEL_ORDER_ID, OrderPojo.class);
        q.setParameter("channelId", channelId);
        q.setParameter("channelOrderId", channelOrderId);
        return q.getSingleResult();
    }

}
