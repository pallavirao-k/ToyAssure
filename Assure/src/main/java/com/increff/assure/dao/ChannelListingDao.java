package com.increff.assure.dao;

import com.increff.assure.pojo.ChannelListingPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChannelListingDao extends GenericDao<ChannelListingPojo> {


    private static String SELECT_BY_CLIENT_ID_CHANNEL_ID_CHANNEL_SKU_ID = "select p from ChannelListingPojo p where clientId=:clientId AND channelId=:channelId AND channelSkuId IN (:channelSkuIds)";
    private static String SELECT_BY_CLIENT_ID_CHANNEL_ID_CHANNEL_SKU_IDD = "select p from ChannelListingPojo p where clientId=:clientId AND channelId=:channelId AND channelSkuId =: channelSkuId";


    public List<ChannelListingPojo> selectByClientIdChannelIdChannelSkuId(Long clientId, Long channelId, List<String> channelSkuIds) {
        List<ChannelListingPojo> channelListingPojoList = new ArrayList<>();
        for (List<String> partitionedChannelSkuIds : partition(channelSkuIds)) {
            TypedQuery<ChannelListingPojo> q = getQuery(SELECT_BY_CLIENT_ID_CHANNEL_ID_CHANNEL_SKU_ID, ChannelListingPojo.class);
            q.setParameter("clientId", clientId);
            q.setParameter("channelId", channelId);
            q.setParameter("channelSkuIds", partitionedChannelSkuIds);
            channelListingPojoList.addAll(q.getResultList());
        }
        return channelListingPojoList;
    }

    public ChannelListingPojo selectByClientIdChannelIdChannelSkuIdd(Long clientId, Long channelId, String channelSkuId) {
            ChannelListingPojo pojo;
            try {
                TypedQuery<ChannelListingPojo> q = getQuery(SELECT_BY_CLIENT_ID_CHANNEL_ID_CHANNEL_SKU_IDD, ChannelListingPojo.class);
                q.setParameter("clientId", clientId);
                q.setParameter("channelId", channelId);
                q.setParameter("channelSkuId", channelSkuId);
                pojo = q.getSingleResult();
            }catch (NoResultException e){
                pojo = null;
            }
        return pojo;
    }

}
