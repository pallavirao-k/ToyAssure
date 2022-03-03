package com.increff.assure.service;

import com.increff.assure.dao.BinDao;
import com.increff.assure.dao.BinSkuDao;
import com.increff.assure.pojo.*;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BinService extends AbstarctService{

    @Autowired
    private BinDao dao;
    @Autowired
    private BinSkuDao binSkuDao;

    public List<BinPojo> add(List<BinPojo> list){
        return dao.insert(list);
    }

    public BinPojo get(Long id) throws ApiException {
        return getCheckBin(id);
    }
    public BinPojo protectedGet(Long id){
        return dao.select(id);
    }

    public List<BinPojo> getAllBins(){
       return dao.selectAll();
    }

    public void addBinSku(BinSkuPojo binSkuPojo) throws ApiException {
        BinSkuPojo binSkuPojoEx = binSkuDao.select(binSkuPojo.getBinId(), binSkuPojo.getGlobalSkuId());
        if(Objects.isNull(binSkuPojoEx)) { binSkuDao.insert(binSkuPojo);return; }
        binSkuPojoEx.setQty(binSkuPojo.getQty());

    }
    public BinSkuPojo getBinSku(Long id) throws ApiException {
        return getCheck(id);
    }

    public List<BinSkuPojo> getAllBinSku(){
        return binSkuDao.selectAll();
    }


    public BinSkuPojo get(Long binId, Long globalSkuId){
        return binSkuDao.select(binId, globalSkuId);
    }

    public void updateQty(BinSkuPojo binSkuPojo, Long qty){
        binSkuPojo.setQty(qty);
    }

    public BinPojo getCheckBin(Long id) throws ApiException {
        BinPojo bp = dao.select(id);
        if (bp == null) {
            throw new ApiException("Bin with given id: "+ id+ " doesn't exist");
        }
        return bp;
    }


    private BinSkuPojo getCheck(Long id) throws ApiException {
        BinSkuPojo p = binSkuDao.select(id);
        if (p == null) {
            throw new ApiException("BinSku with given id: "+ id+ " doesn't exist");
        }
        return p;
    }







}
