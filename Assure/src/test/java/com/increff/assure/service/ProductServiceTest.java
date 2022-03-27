package com.increff.assure.service;

import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.commons.Exception.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testInsert() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1).
                getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);

        List<ProductPojo> productPojo_after = productService.getAll();
        assertEquals(productPojoList_before.size()+1, productPojo_after.size());
        assertEquals("air-force2", productService.get(productPojo.getGlobalSkuId()).getProductName());
    }

    @Test
    public void testInsert2() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1).
                getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);
        productService.add(productPojo.getClientId(), productPojoList);

        List<ProductPojo> productPojo_after = productService.getAll();
        assertEquals(productPojoList_before.size()+1, productPojo_after.size());
        assertEquals("air-force2", productService.get(productPojo.getGlobalSkuId()).getProductName());
    }

    @Test
    public void testSelectAll() throws ApiException {
        List<ProductPojo> list_before = productService.getAll();
        preliminaryInsert();
        List<ProductPojo> list_after = productService.getAll();
        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testSelect() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);

        assertEquals("air-force2", productService.get(productPojo.getGlobalSkuId()).getProductName());
    }

    @Test
    public void testSelectByClientSkuIdAndClientId() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);

        assertEquals("air-force2", productService.getByClientSkuIdAndClientId(productPojo.getClientSkuId(),
                productPojo.getClientId()).getProductName());
    }

    @Test
    public void testSelectByClientSkuIdAndClientIds() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);

        List<String> clientSkuIds = new ArrayList<>();
        clientSkuIds.add(productPojoList_before.get(productPojoList_before.size()-1).getClientSkuId());
        clientSkuIds.add(productPojo.getClientSkuId());
        assertEquals("air-force1", productService.getByClientSkuIdsAndClientId(productPojo.getClientId(),
                clientSkuIds).get(0).getProductName());
        assertEquals("air-force2", productService.getByClientSkuIdsAndClientId(productPojo.getClientId(),
                clientSkuIds).get(1).getProductName());

    }

    @Test
    public void testSelectByGlobalSkuIds() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);

        List<Long> globalSkuIds = new ArrayList<>();
        globalSkuIds.add(productPojoList_before.get(productPojoList_before.size()-1).getGlobalSkuId());
        globalSkuIds.add(productPojo.getGlobalSkuId());
        assertEquals("air-force1", productService.getByGlobalSkuIds(globalSkuIds).get(productPojoList_before
                .get(productPojoList_before.size()-1).getGlobalSkuId()).getProductName());
        assertEquals("air-force2", productService.getByGlobalSkuIds(globalSkuIds).get(productPojo
                .getGlobalSkuId()).getProductName());
    }

    @Test
    public void testSelectByClientId() throws ApiException{
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);

        assertEquals("air-force1", productService.getByClientId(productPojo.getClientId()).get(0)
                .getProductName());
        assertEquals("air-force2", productService.getByClientId(productPojo.getClientId()).get(1)
                .getProductName());

    }

    @Test
    public void testUpdate() throws ApiException {
        preliminaryInsert();
        List<ProductPojo> productPojoList_before = productService.getAll();
        ProductPojo productPojo = getProductPojo(productPojoList_before.get(productPojoList_before.size()-1)
                .getClientId());
        List<ProductPojo> productPojoList = new ArrayList<>();
        productPojoList.add(productPojo);
        productService.add(productPojo.getClientId(), productPojoList);

        ProductPojo productPojoUpdate = new ProductPojo();
        productPojoUpdate.setProductMrp(200.0);
        productPojoUpdate.setDescription("new desc");
        productPojo.setProductName("air-force2");
        productPojo.setBrandId("bi2");

        productService.update(productPojo.getGlobalSkuId(), productPojoUpdate);

        assertEquals("new desc", productService.get(productPojo.getGlobalSkuId()).getDescription());
        assertEquals(Double.valueOf(200), productService.get(productPojo.getGlobalSkuId()).getProductMrp());


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
