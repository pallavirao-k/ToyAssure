package com.increff.assure.dao;

import com.increff.assure.pojo.ChannelListingPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ChannelListingDao extends GenericDao<ChannelListingPojo> {


    private static String SELECT_BY_CLIENT_ID_CHANNEL_ID_CHANNEL_SKU_ID = "select p from ChannelListingPojo p where clientId=:clientId AND channelId=:channelId AND channelSkuId IN (:channelSkuIds)";


    public List<ChannelListingPojo> selectByClientIdChannelIdChannelSkuId(Long clientId, Long channelId, List<String> channelSkuIds){
        TypedQuery<ChannelListingPojo> q = getQuery(SELECT_BY_CLIENT_ID_CHANNEL_ID_CHANNEL_SKU_ID, ChannelListingPojo.class);
        q.setParameter("clientId", clientId);
        q.setParameter("channelId", channelId);
        q.setParameter("channelSkuIds", channelSkuIds);
        return q.getResultList();
    }



}
