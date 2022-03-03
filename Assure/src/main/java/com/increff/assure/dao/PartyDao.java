package com.increff.assure.dao;

import com.increff.assure.pojo.PartyPojo;
import com.increff.commons.Constants.Party.PartyType;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
public class PartyDao extends GenericDao<PartyPojo> {



    private final static String SELECT_BY_NAME_AND_TYPE = "select p from PartyPojo p where partyName=:name AND partyType=:type";
    private final static String SELECT_BY_TYPE = "select p from PartyPojo p where partyType=:type";
// add and
// remove enum
    public PartyPojo selectByNameAndType(String name, PartyType type){
        PartyPojo p;
        try {
            TypedQuery<PartyPojo> q = getQuery(SELECT_BY_NAME_AND_TYPE, PartyPojo.class);
            q.setParameter("name", name);
            q.setParameter("type", type);
            p = q.getSingleResult();
        }catch(NoResultException e){
            p=null;
        }
        return p;
    }

    public List<PartyPojo> selectByType(PartyType p){
        TypedQuery<PartyPojo> q = getQuery(SELECT_BY_TYPE, PartyPojo.class);
        q.setParameter("type", p);
        return q.getResultList();
    }


}
