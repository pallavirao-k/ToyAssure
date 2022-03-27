package com.increff.assure.dao;

import com.increff.assure.pojo.BinPojo;
import com.increff.assure.pojo.BinSkuPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BinSkuDao extends GenericDao<BinSkuPojo> {
    private static String SELECT_BY_BINID_GLOBAL_SKUID = "select p from BinSkuPojo p where binId=:binId AND globalSkuId=:globalSkuId ";
    private static String SELECT_BY_GLOBAL_SKU_ID = "select p from BinSkuPojo p where globalSkuId=:globalSkuId";
    private static String SELECT_BY_BINID_OR_GLOBAL_SKUID = "select p from BinSkuPojo p "
            + "where (binId = :binId OR :binId IS NULL)  AND "
            + "(globalSkuId = :globalSkuId OR :globalSkuId IS NULL)";

    public BinSkuPojo select(Long binId, Long globalSkuId){
        BinSkuPojo bsp;
        try {
            TypedQuery<BinSkuPojo> q = getQuery(SELECT_BY_BINID_GLOBAL_SKUID, BinSkuPojo.class);
            q.setParameter("binId", binId);
            q.setParameter("globalSkuId", globalSkuId);
            bsp = q.getSingleResult();
        }catch (NoResultException e){
            bsp = null;
        }
        return bsp;
    }

    public List<BinSkuPojo> selectByGlobalSkuId(Long globalSkuId){
        TypedQuery<BinSkuPojo> q = getQuery(SELECT_BY_GLOBAL_SKU_ID, BinSkuPojo.class);
        q.setParameter("globalSkuId", globalSkuId);
        return q.getResultList();
    }

    public List<BinSkuPojo> search(Long binId, Long globalSkuId){
        List<BinSkuPojo> bsp;
        try {
            TypedQuery<BinSkuPojo> q = getQuery(SELECT_BY_BINID_OR_GLOBAL_SKUID, BinSkuPojo.class);
            q.setParameter("binId", binId);
            q.setParameter("globalSkuId", globalSkuId);
            bsp = q.getResultList();
        }catch (NoResultException e){
            bsp = new ArrayList<>();
        }
        return bsp;
    }



}
