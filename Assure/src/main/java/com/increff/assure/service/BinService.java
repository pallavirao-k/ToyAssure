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
       return dao.selectAll();
    }

    public void addBinSku(BinSkuPojo binSkuPojo) throws ApiException {
        BinSkuPojo binSkuPojoEx = binSkuDao.select(binSkuPojo.getBinId(), binSkuPojo.getGlobalSkuId());
        if(Objects.nonNull(binSkuPojoEx)) {
            binSkuPojoEx.setQty(binSkuPojo.getQty()+binSkuPojo.getQty());
            return;
        }
        binSkuDao.insert(binSkuPojo);

    }
    public BinSkuPojo getBinSku(Long id) throws ApiException {
        return getCheck(id);
    }

    public List<BinSkuPojo> getAllBinSku() throws ApiException {
        return binSkuDao.selectAll();
    }


    public void updateBinSkuQty(Long qtyToReduce, Long globalSkuId){
        if(qtyToReduce<=0){
            return;
        }
        List<BinSkuPojo> binSkuPojoList = getByGlobalSkuId(globalSkuId);
        for(BinSkuPojo binSkuPojo:binSkuPojoList){
            if(qtyToReduce==0){
                break;
            }
            Long qtyToPut = Math.min(qtyToReduce, binSkuPojo.getQty());
            binSkuPojo.setQty(binSkuPojo.getQty()-qtyToPut);
            qtyToReduce = qtyToReduce-qtyToPut;

        }

    }

    public List<BinSkuPojo> getByGlobalSkuId(Long globalSkuId){
        return binSkuDao.selectByGlobalSkuId(globalSkuId);
    }

    public BinSkuPojo get(Long binId, Long globalSkuId){
        return binSkuDao.select(binId, globalSkuId);
    }

    public Long updateQty(Long id, Long qty){
        BinSkuPojo binSkuPojo = binSkuDao.select(id);
        Long qtyToAdd = qty - binSkuPojo.getQty();
        binSkuPojo.setQty(qty);
        return qtyToAdd;
    }

    public BinPojo getCheckBin(Long id) throws ApiException {
        BinPojo bp = dao.select(id);
        if (bp == null) {
            throw new ApiException("Bin with given id: "+ id+ " doesn't exist");
        }
        return bp;
    }

    public List<BinSkuPojo> search(Long binId, Long globalSkuId){
        return binSkuDao.search(binId, globalSkuId);
    }

    private BinSkuPojo getCheck(Long id) throws ApiException {
        BinSkuPojo p = binSkuDao.select(id);
        if (p == null) {
            throw new ApiException("BinSku with given id: "+ id+ " doesn't exist");
        }
        return p;
    }







}
