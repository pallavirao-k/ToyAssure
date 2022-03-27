package com.increff.assure.dao;

import com.increff.assure.pojo.BinPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.commons.Exception.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BinDaoTest extends AbstractUnitTest {

    @Autowired
    private BinDao binDao;

    @Test
    public void testInsert() throws ApiException {
        preliminaryInsert();
        List<BinPojo> binPojos = binDao.insert(getBinPojo());
        assertEquals(1, binPojos.size());
    }

    @Test
    public void testSelectAllBinPojosWithBinIds() throws ApiException {
        preliminaryInsert();
        List<BinPojo> binPojos = binDao.insert(getBinPojo());
        List<Long> binIds = binPojos.stream().map(x -> x.getBinId()).collect(Collectors.toList());
        assertEquals(binPojos.get(0).getBinId(), binDao.selectAllBinPojoWithBinIds(binIds).get(0).getBinId());

    }


    private List<BinPojo> getBinPojo(){
        List<BinPojo> binPojos = new ArrayList<>();
        binPojos.add(new BinPojo());
        return binPojos;
    }
}
