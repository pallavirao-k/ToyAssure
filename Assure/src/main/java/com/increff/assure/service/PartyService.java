package com.increff.assure.service;

import com.increff.assure.dao.PartyDao;
import com.increff.assure.pojo.PartyPojo;
import com.increff.commons.Constants.Party.PartyType;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;



@Service
@Transactional(rollbackOn = ApiException.class)
public class PartyService extends AbstarctService {

    @Autowired
    private PartyDao dao;


    public void add(PartyPojo p){
        PartyPojo exists = dao.selectByNameAndType(p.getPartyName(), p.getPartyType());
        if(Objects.nonNull(exists)) {
            return;
        }
        dao.insert(p);
    }


    public PartyPojo get(Long id) throws  ApiException{
        return getCheck(id);
    }

    public List<PartyPojo> getAll(){
        return dao.selectAll();
    }

    public List<PartyPojo> getPartyByType(PartyType p){
        return dao.selectByType(p);
    }

    public PartyPojo getCheck(Long id) throws ApiException {
        PartyPojo p = dao.select(id);
        if (Objects.isNull(p)) throw new ApiException("party with given id: "+ id+ " doesn't exist");
        return p;
    }






}
