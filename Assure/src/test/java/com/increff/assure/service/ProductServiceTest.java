package com.increff.assure.service;

import com.increff.assure.dao.PartyDao;
import com.increff.assure.dao.ProductDao;
import com.increff.assure.pojo.PartyPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.assure.spring.TestPojo;
import com.increff.commons.Constants.Party;
import com.increff.commons.Exception.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.increff.assure.spring.TestPojo.createMemberPojo;
import static com.increff.assure.spring.TestPojo.createProductPojo;
import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private PartyDao partyDao;
    @Autowired
    private ProductDao productDao;

    @Test
    public void testAddValid1() throws ApiException {

        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productDao.selectAll();

        assertEquals(listBefore.size()+1, listAfter .size());
        assertEquals("p1", productDao.select(productPojo.getGlobalSkuId()).getProductName());
    }

    /*  when combination of client Id and ClientSkuId is repeated*/
    @Test
    public void testAddValid2() throws ApiException {
        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo, productPojo}));

        List<ProductPojo> listAfter = productDao.selectAll();

        assertEquals(listBefore.size()+1, listAfter .size());
        assertEquals("p1", productDao.select(productPojo.getGlobalSkuId()).getProductName());
    }

    @Test
    public void testSelectAll() throws ApiException {
        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productService.getAll();

        assertEquals(listBefore.size()+1, listAfter .size());
    }

    @Test
    public void testSelect() throws ApiException {
        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productDao.selectAll();

        assertEquals(listBefore.size()+1, listAfter .size());
        assertEquals("p1", productService.get(productPojo.getGlobalSkuId()).getProductName());
    }

    @Test
    public void testSelectByClientSkuIdAndClientId() throws ApiException {
        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productDao.selectAll();

        assertEquals(listBefore.size()+1, listAfter .size());
        assertEquals("p1", productService.get(productPojo.getGlobalSkuId()).getProductName());
        assertEquals("p1", productService.getByClientSkuIdAndClientId(productPojo.getClientSkuId(),
                productPojo.getClientId()).getProductName());
    }

    @Test
    public void testSelectByClientSkuIdAndClientIds() throws ApiException {
        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productDao.selectAll();

        List<String> clientSkuIds = new ArrayList<>();
        clientSkuIds.add(productPojo.getClientSkuId());
        assertEquals("p1", productService.getByClientSkuIdsAndClientId(productPojo.getClientId(),
                clientSkuIds).get(0).getProductName());
        assertEquals("p1", productService.get(productPojo.getGlobalSkuId()).getProductName());

    }

    @Test
    public void testSelectByGlobalSkuIds() throws ApiException {
        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productDao.selectAll();


        List<Long> globalSkuIds = new ArrayList<>();
        globalSkuIds.add(productPojo.getGlobalSkuId());
        assertEquals("p1", productService.getByGlobalSkuIds(globalSkuIds).get(globalSkuIds
                .get(globalSkuIds.size()-1)).getProductName());
    }

    @Test
    public void testSelectByClientId() throws ApiException{

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productService.getByClientId(productPojo.getClientId());


        assertEquals("p1", listAfter.get(listAfter.size()-1).getProductName());
        assertEquals("b1", listAfter.get(listAfter.size()-1).getBrandId());


    }

    @Test
    public void testUpdate() throws ApiException {
        List<ProductPojo> listBefore = productDao.selectAll();

        PartyPojo partyPojo = createMemberPojo("m1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojo);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskuid1", "desc1"
                , 10.0, partyPojo.getPartyId());
        productService.add(productPojo.getClientId(), Arrays.asList(new ProductPojo[]{productPojo}));

        List<ProductPojo> listAfter = productDao.selectAll();


        ProductPojo productPojoUpdate = new ProductPojo();
        productPojoUpdate.setProductMrp(200.0);
        productPojoUpdate.setDescription("new desc");
        productPojo.setProductName("air-force2");
        productPojo.setBrandId("bi2");

        productService.update(productPojo.getGlobalSkuId(), productPojoUpdate);

        assertEquals("new desc", productService.get(productPojo.getGlobalSkuId()).getDescription());
        assertEquals(Double.valueOf(200), productService.get(productPojo.getGlobalSkuId()).getProductMrp());


    }




}
