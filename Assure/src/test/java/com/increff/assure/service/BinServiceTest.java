package com.increff.assure.service;


import com.increff.assure.dao.BinDao;
import com.increff.assure.dao.BinSkuDao;
import com.increff.assure.dao.PartyDao;
import com.increff.assure.dao.ProductDao;
import com.increff.assure.pojo.BinPojo;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.PartyPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.assure.spring.TestPojo;
import com.increff.commons.Constants.Party;
import com.increff.commons.Data.BinData;
import com.increff.commons.Exception.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.increff.assure.spring.TestPojo.*;
import static org.junit.Assert.assertEquals;

public class BinServiceTest extends AbstractUnitTest {

    @Autowired
    private BinService binService;

    @Autowired
    private BinDao binDao;
    @Autowired
    private BinSkuDao binSkuDao;
    @Autowired
    private PartyDao partyDao;
    @Autowired
    private ProductDao productDao;


    @Test
    public void testAdd() throws ApiException {
        List<BinPojo> list_before = binDao.selectAll();
        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binService.add(binPojos);

        List<BinPojo> list_after = binDao.selectAll();
        assertEquals(list_before.size()+binCreated.size(), list_after.size());
    }

    @Test
    public void testGetCheckBinIdList() throws ApiException {
        List<BinPojo> list_before = binDao.selectAll();
        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binService.add(binPojos);
        List<BinPojo> list_after = binDao.selectAll();

        List<Long> binIds = binCreated.stream().map(x -> x.getBinId()).collect(Collectors.toList());
        assertEquals(binCreated.get(binCreated.size()-1).getBinId(), binService.getCheckBinIdList(binIds).get(binCreated.size()-1).getBinId());

    }

    @Test
    public void testGet() throws ApiException {
        List<BinPojo> list_before = binDao.selectAll();
        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binService.add(binPojos);

        assertEquals(binPojos.get(binCreated.size()-1).getBinId(),
                binService.get(binPojos.get(binCreated.size()-1).getBinId()).getBinId());
    }

    @Test
    public void testGetAllBins() throws ApiException {
        List<BinPojo> list_before = binDao.selectAll();
        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binService.add(binPojos);

        List<BinPojo> list_after = binService.getAllBins();

        assertEquals(list_before.size()+binCreated.size(), list_after.size());
    }

    @Test
    public void testAddBinSku() throws ApiException {

        List<BinSkuPojo> list_before = binSkuDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojo.getPartyId());
        productDao.insert(productPojo);

        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binDao.insert(binPojos);
        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());

        binService.addBinSku(binSkuPojo);
        List<BinSkuPojo> list_after = binSkuDao.selectAll();

        assertEquals(list_before.size()+1, list_after.size());
        assertEquals(Long.valueOf(100), binSkuDao.select(binSkuPojo.getId()).getQty());

    }

    /* when binSkuPojo already present then it updates*/
    @Test
    public void testAddBinSku2() throws ApiException {

        List<BinSkuPojo> list_before = binSkuDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuiuiuiud1", "desc1",
                10.0 ,partyPojo.getPartyId());
        productDao.insert(productPojo);

        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binDao.insert(binPojos);
        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());
        binService.addBinSku(binSkuPojo);

        BinSkuPojo binSkuPojo2 = createBinSkuPojo(40L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());
        binService.addBinSku(binSkuPojo2);

        List<BinSkuPojo> list_after = binSkuDao.selectAll();

        assertEquals(list_before.size()+1, list_after.size());
        assertEquals(Long.valueOf(140), binSkuDao.select(binSkuPojo.getId()).getQty());

    }

    @Test
    public void testGetBinSku() throws ApiException {

        List<BinSkuPojo> list_before = binSkuDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojo.getPartyId());
        productDao.insert(productPojo);

        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binDao.insert(binPojos);
        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());
        binService.addBinSku(binSkuPojo);

        assertEquals(binSkuPojo.getGlobalSkuId(), binService.getBinSku(binSkuPojo.getId()).getGlobalSkuId());
        assertEquals(binSkuPojo.getBinId(), binService.getBinSku(binSkuPojo.getId()).getBinId());
        assertEquals(binSkuPojo.getQty(), binService.getBinSku(binSkuPojo.getId()).getQty());
    }

    @Test
    public void testGetAllBinSku() throws ApiException {

        List<BinSkuPojo> list_before = binSkuDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojo.getPartyId());
        productDao.insert(productPojo);

        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binDao.insert(binPojos);
        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());

        binService.addBinSku(binSkuPojo);
        List<BinSkuPojo> list_after = binService.getAllBinSku();

        assertEquals(list_before.size()+1, list_after.size());

    }

    @Test
    public void testUpdateBinSkuQty() throws ApiException {

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojo.getPartyId());
        productDao.insert(productPojo);

        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binDao.insert(binPojos);
        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());

        binService.addBinSku(binSkuPojo);

        binService.updateBinSkuQty(50L, binSkuPojo.getGlobalSkuId());
        List<BinSkuPojo> binSkuPojoList = binService.getByGlobalSkuId(binSkuPojo.getGlobalSkuId());
        Long qty =0L;
        for(BinSkuPojo binSkuPojo1: binSkuPojoList){
            qty+=binSkuPojo1.getQty();
        }

        assertEquals(Long.valueOf(50), qty);

    }

    @Test
    public void testGetBinSkuByBinIdAndGlobalSkuId() throws ApiException {
        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojo.getPartyId());
        productDao.insert(productPojo);

        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binDao.insert(binPojos);
        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());

        binService.addBinSku(binSkuPojo);

        assertEquals(binSkuPojo.getGlobalSkuId(), binService.get(binSkuPojo.getBinId(), binSkuPojo.getGlobalSkuId())
                .getGlobalSkuId());
    }


    @Test
    public void testUpdate() throws ApiException {

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojo.getPartyId());
        productDao.insert(productPojo);

        Long binCount = 5L;
        List<BinPojo> binPojos = createBinPojo(binCount);
        List<BinPojo> binCreated = binDao.insert(binPojos);
        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), binCreated.get(0).getBinId());

        binService.addBinSku(binSkuPojo);

        binService.updateQty(binSkuPojo.getId(), 100L);

        assertEquals(Long.valueOf(100), binService.getBinSku(binSkuPojo.getId()).getQty());
    }


}
