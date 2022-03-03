package com.increff.assure.dao;

import com.increff.assure.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class InventoryDao extends GenericDao<InventoryPojo> {

    private static String SELECT_BY_GLOBAL_SKU_ID = "select p from InventoryPojo p where globalSkuId=:globalSkuId";
    private final static String SELECT_INVENTORY_BY_GLOBAL_SKU_ID = "select p from InventoryPojo p where globalSkuId=:id";

    public InventoryPojo selectByGlobalSkuId(Long globalSkuId){
        InventoryPojo ip;
        try {
            TypedQuery<InventoryPojo> q = getQuery(SELECT_BY_GLOBAL_SKU_ID, InventoryPojo.class);
            q.setParameter("globalSkuId", globalSkuId);
            ip = q.getSingleResult();
        }catch(NoResultException e){
            ip=null;
        }
        return ip;
    }

}
