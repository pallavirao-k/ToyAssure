package com.increff.assure.service;


import com.increff.assure.pojo.BinPojo;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.commons.Exception.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BinServiceTest extends AbstractUnitTest {

    @Autowired
    private BinService binService;

    @Test
    public void testInsert() throws ApiException {
        preliminaryInsert();
        List<BinPojo> binPojos = binService.add(getBinPojo());
        assertEquals(1, binPojos.size());
    }

    @Test
    public void testGetCheckBinIdList() throws ApiException {
        preliminaryInsert();
        List<BinPojo> binPojos = binService.add(getBinPojo());
        List<Long> binIds = binPojos.stream().map(x -> x.getBinId()).collect(Collectors.toList());
        assertEquals(binPojos.get(0).getBinId(), binService.getCheckBinIdList(binIds).get(0).getBinId());

    }

    @Test
    public void testGet() throws ApiException {
        preliminaryInsert();
        List<BinPojo> binPojos = binService.add(getBinPojo());
        assertEquals(binPojos.get(0).getBinId(), binService.get(binPojos.get(0).getBinId()).getBinId());
    }

    @Test
    public void testGetAllBins() throws ApiException {
        preliminaryInsert();
        List<BinPojo> list_before = binService.getAllBins();
        List<BinPojo> binPojos = binService.add(getBinPojo());
        List<BinPojo> list_after = binService.getAllBins();

        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testAddBinSku() throws ApiException {
        preliminaryInsert();
        List<BinSkuPojo> list_before = binService.getAllBinSku();
        BinSkuPojo binSkuPojo = getBinSkuPojo(list_before.get(list_before.size()-1).getBinId()+1, list_before
                .get(list_before.size()-1).getGlobalSkuId());
        binService.addBinSku(binSkuPojo);
        List<BinSkuPojo> list_after = binService.getAllBinSku();

        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testGetBinSku() throws ApiException {
        preliminaryInsert();
        List<BinSkuPojo> list_before = binService.getAllBinSku();
        BinSkuPojo binSkuPojo = getBinSkuPojo(list_before.get(list_before.size()-1).getBinId()+1, list_before
                .get(list_before.size()-1).getGlobalSkuId());
        binService.addBinSku(binSkuPojo);

        assertEquals(binSkuPojo.getGlobalSkuId(), binService.getBinSku(binSkuPojo.getId()).getGlobalSkuId());
    }

    @Test
    public void testGetAllBinSku() throws ApiException {
        preliminaryInsert();
        List<BinSkuPojo> list_before = binService.getAllBinSku();
        BinSkuPojo binSkuPojo = getBinSkuPojo(list_before.get(list_before.size()-1).getBinId()+1, list_before
                .get(list_before.size()-1).getGlobalSkuId());
        binService.addBinSku(binSkuPojo);
        List<BinSkuPojo> list_after = binService.getAllBinSku();

        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testUpdateBinSkuQty() throws ApiException {
        preliminaryInsert();
        List<BinSkuPojo> list_before = binService.getAllBinSku();
        BinSkuPojo binSkuPojo = getBinSkuPojo(list_before.get(list_before.size()-1).getBinId()+1, list_before
                .get(list_before.size()-1).getGlobalSkuId());
        binService.addBinSku(binSkuPojo);
        binService.updateBinSkuQty(250L, binSkuPojo.getGlobalSkuId());
        List<BinSkuPojo> binSkuPojoList = binService.getByGlobalSkuId(binSkuPojo.getGlobalSkuId());
        Long qty =0L;
        for(BinSkuPojo binSkuPojo1: binSkuPojoList){
            qty+=binSkuPojo1.getQty();
        }

        assertEquals(Long.valueOf(50), qty);

    }

    @Test
    public void testGetBinSkuByBinIdAndGlobalSkuId() throws ApiException {
        preliminaryInsert();
        List<BinSkuPojo> list_before = binService.getAllBinSku();
        BinSkuPojo binSkuPojo = getBinSkuPojo(list_before.get(list_before.size()-1).getBinId()+1, list_before
                .get(list_before.size()-1).getGlobalSkuId());
        binService.addBinSku(binSkuPojo);

        assertEquals(binSkuPojo.getGlobalSkuId(), binService.get(binSkuPojo.getBinId(), binSkuPojo.getGlobalSkuId())
                .getGlobalSkuId());
    }


    @Test
    public void testUpdate() throws ApiException {
        preliminaryInsert();
        List<BinSkuPojo> list_before = binService.getAllBinSku();
        BinSkuPojo binSkuPojo = getBinSkuPojo(list_before.get(list_before.size()-1).getBinId()+1, list_before
                .get(list_before.size()-1).getGlobalSkuId());
        binService.addBinSku(binSkuPojo);
        binService.updateQty(binSkuPojo.getId(), 100L);
        assertEquals(Long.valueOf(100), binService.getBinSku(binSkuPojo.getId()).getQty());
    }


    private List<BinPojo> getBinPojo(){
        List<BinPojo> binPojos = new ArrayList<>();
        binPojos.add(new BinPojo());
        return binPojos;
    }

    private BinSkuPojo getBinSkuPojo(Long binId, Long globalSkuId){
        BinSkuPojo binSkuPojo = new BinSkuPojo();
        binSkuPojo.setQty(200L);
        binSkuPojo.setBinId(binId);
        binSkuPojo.setGlobalSkuId(globalSkuId);
        return binSkuPojo;
    }
}
