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
    public List<BinPojo> getCheckBinIdList(List<Long> binIds){
        return dao.selectAllBinPojoWithBinIds(binIds);
    }

    public List<BinPojo> getAllBins(){
       return dao.selectAll();// check in case of empty list.
    }

    public void uploadBinSku(BinSkuPojo binSkuPojo) throws ApiException {
        BinSkuPojo binSkuPojoEx = binSkuDao.select(binSkuPojo.getBinId(), binSkuPojo.getGlobalSkuId());
        if(Objects.isNull(binSkuPojoEx)) {
            binSkuDao.insert(binSkuPojo);
            return; // avoid return
        }
        binSkuPojoEx.setQty(binSkuPojo.getQty());

    }
    public BinSkuPojo getBinSku(Long id) throws ApiException {
        return getCheck(id);
    }


    public void updateBinSKuQty(Long allocatedQty, Long globalSkuId){
        List<BinSkuPojo> binSkuPojoList = binSkuDao.selectByGlobalSkuId(globalSkuId);
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            binSkuPojo.setQty(binSkuPojo.getQty()-Math.min(allocatedQty, binSkuPojo.getQty()));
            allocatedQty = allocatedQty-Math.min(allocatedQty,binSkuPojo.getQty());
            if(binSkuPojo.getQty()==0){
                break;
            }
        }
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
