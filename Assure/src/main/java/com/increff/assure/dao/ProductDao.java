package com.increff.assure.dao;

import com.increff.assure.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao extends GenericDao<ProductPojo> {

    private static final String SELECT_BY_CLIENT_SKU_ID_CLIENT_ID = "select p from ProductPojo p where clientSkuId =: clientSkuId AND clientId =: clientId";
    private static final String SELECT_BY_CLIENT_ID_AND_CLIENT_SKU_IDS = "select p from ProductPojo p where clientId =: clientId AND clientSkuId IN (:clientSkuIds)";


    public ProductPojo selectByClientSkuIdAndClientId(String clientSkuId, Long clientId){
        ProductPojo p;
        try {
            TypedQuery<ProductPojo> q = getQuery(SELECT_BY_CLIENT_SKU_ID_CLIENT_ID, ProductPojo.class);
            q.setParameter("clientSkuId", clientSkuId);
            q.setParameter("clientId", clientId);
            p = q.getSingleResult();
        }catch (NoResultException e){
            p=null;
        }
        return p;
    }

    public List<ProductPojo> selectByClientIdAndClientSkuIds(Long clientId, List<String> clientSkuIds){
        List<ProductPojo> finalList = new ArrayList<>();
        for(List<String> partitionedClientSkuIds :partition(clientSkuIds)) {
            TypedQuery<ProductPojo> q = getQuery(SELECT_BY_CLIENT_ID_AND_CLIENT_SKU_IDS, ProductPojo.class);
            q.setParameter("clientId", clientId);
            q.setParameter("clientSkuIds", partitionedClientSkuIds);
            finalList.addAll(q.getResultList());
        }
        return finalList;
    }

    // test for no result exception ie .
    // add singleOrNull function in Generic Dao.









}
