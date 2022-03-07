package com.increff.assure.dao;

import com.increff.assure.pojo.BinPojo;
import com.increff.assure.pojo.BinSkuPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BinDao extends GenericDao<BinPojo> {


    private static final String SELECT_ALL_BINS_USING_BIN_IDS = "select p from BinPojo p where binId IN (:binIds)";


    public List<BinPojo> insert(List<BinPojo> list){
        List<BinPojo> returnList = new ArrayList<>();
        for(BinPojo bp : list){
            em.persist(bp);
            returnList.add(bp);
        }
        return returnList;
    }

    public List<BinPojo> selectAllBinPojoWithBinIds(List<Long> binIds){
        TypedQuery<BinPojo> q = getQuery(SELECT_ALL_BINS_USING_BIN_IDS, BinPojo.class);
        q.setParameter("binIds", binIds);
        return q.getResultList();
    }








}
