package com.increff.assure.dao;

import com.increff.assure.pojo.BinPojo;
import com.increff.assure.pojo.BinSkuPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BinDao extends GenericDao<BinPojo> {

    private static final String SELECT_ALL_BIN_SKU = "select p from BinSkuPojo p";


    public List<BinPojo> insert(List<BinPojo> list){
        List<BinPojo> returnList = new ArrayList<>();
        for(BinPojo bp : list){
            em.persist(bp);
            returnList.add(bp);
        }
        return returnList;
    }

    public List<BinSkuPojo> selectAllBinSku(){
        TypedQuery<BinSkuPojo> q = getQuery(SELECT_ALL_BIN_SKU, BinSkuPojo.class);
        return q.getResultList();
    }






}
