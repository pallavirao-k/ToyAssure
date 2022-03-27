package com.increff.assure.dao;

import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.commons.Exception.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends AbstractUnitTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsert() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productDao.selectAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(0).getClientId());
        productDao.insert(productPojo);

        List<ProductPojo> productPojo_after = productDao.selectAll();
        assertEquals(productPojoList_before.size()+1, productPojo_after.size());
        assertEquals("air-force2", productDao.select(productPojo.getGlobalSkuId()).getProductName());
    }

    @Test
    public void testSelectAll() throws ApiException {
        List<ProductPojo> list_before = productDao.selectAll();
        preliminaryInsert();
        List<ProductPojo> list_after = productDao.selectAll();
        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testSelect() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productDao.selectAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        productDao.insert(productPojo);
        assertEquals("air-force2", productDao.select(productPojo.getGlobalSkuId()).getProductName());
    }

    @Test
    public void testSelectByClientSkuIdAndClientId() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productDao.selectAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        productDao.insert(productPojo);

        assertEquals("air-force2", productDao.selectByClientSkuIdAndClientId(productPojo.getClientSkuId(),
                productPojo.getClientId()).getProductName());
    }

    @Test
    public void testSelectByClientSkuIdAndClientIds() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productDao.selectAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        productDao.insert(productPojo);
        List<String> clientSkuIds = new ArrayList<>();
        clientSkuIds.add(productPojoList_before.get(productPojoList_before.size()-1).getClientSkuId());
        clientSkuIds.add(productPojo.getClientSkuId());
        assertEquals("air-force1", productDao.selectByClientIdAndClientSkuIds(productPojo.getClientId(),
                clientSkuIds).get(0).getProductName());
        assertEquals("air-force2", productDao.selectByClientIdAndClientSkuIds(productPojo.getClientId(),
                clientSkuIds).get(1).getProductName());

    }

    @Test
    public void testSelectByGlobalSkuId() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productDao.selectAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        productDao.insert(productPojo);
        List<Long> globalSkuIds = new ArrayList<>();
        globalSkuIds.add(productPojoList_before.get(productPojoList_before.size()-1).getGlobalSkuId());
        globalSkuIds.add(productPojo.getGlobalSkuId());
        assertEquals("air-force1", productDao.selectByGlobalSkuIds(globalSkuIds).get(0).getProductName());
        assertEquals("air-force2", productDao.selectByGlobalSkuIds(globalSkuIds).get(1).getProductName());
    }

    @Test
    public void testSelectByClientId() throws ApiException{
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productDao.selectAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        productDao.insert(productPojo);
        assertEquals("air-force1", productDao.selectByClientId(productPojo.getClientId()).get(0)
                .getProductName());
        assertEquals("air-force2", productDao.selectByClientId(productPojo.getClientId()).get(1)
                .getProductName());

    }





    private ProductPojo getProductPojo(Long clientId){

        ProductPojo productPojo = new ProductPojo();
        productPojo.setProductName("air-force2");
        productPojo.setBrandId("bi2");
        productPojo.setProductMrp(10.0);
        productPojo.setClientId(clientId);
        productPojo.setClientSkuId("cskuid2");
        productPojo.setDescription("desc2");
        return productPojo;
    }
}
