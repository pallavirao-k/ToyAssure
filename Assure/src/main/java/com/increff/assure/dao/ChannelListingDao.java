package com.increff.assure.dao;

import com.increff.assure.pojo.ChannelListingPojo;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ChannelListingDao extends GenericDao<ChannelListingPojo> {

    private static String SELECT_BY_GLOBAL_SKU_ID = "select p from ChannelListingPojo where globalSkuId=:globalSkuId";
    private static String SELECT_BY_CHANNEL_ID_CHANNEL_SKU_ID_CLIENT_ID = "select p from ChannelListingPojo p where channelId=:channelId AND channelSkuId=:channelSkuId AND clientId=:clientId";

    public ChannelListingPojo selectByGlobalSkuId(Long globalSkuId){
        TypedQuery<ChannelListingPojo> q = getQuery(SELECT_BY_GLOBAL_SKU_ID, ChannelListingPojo.class);
        q.setParameter("globalSkuId", globalSkuId);
        return q.getSingleResult();
    }
    public ChannelListingPojo selectByChannelAndClient(Long channelId, String channelSkuId, Long clientId){
        TypedQuery<ChannelListingPojo> q = getQuery(SELECT_BY_CHANNEL_ID_CHANNEL_SKU_ID_CLIENT_ID, ChannelListingPojo.class);
        q.setParameter("channelId", channelId);
        q.setParameter("channelSkuId", channelSkuId);
        q.setParameter("clientId", clientId);
        return q.getSingleResult();
    }


}
