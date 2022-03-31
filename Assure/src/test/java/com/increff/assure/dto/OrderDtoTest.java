package com.increff.assure.dto;

import com.increff.assure.dao.*;
import com.increff.assure.pojo.*;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.commons.Constants.Invoice;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Constants.Party;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderItemWithClientSkuId;
import com.increff.commons.Form.OrderWithClientSkuIdForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.increff.assure.spring.TestPojo.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OrderDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderDto dto;

    @Autowired
    private PartyDao partyDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BinDao binDao;
    @Autowired
    private BinSkuDao binSkuDao;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private ChannelListingDao channelListingDao;
    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private InventoryDao inventoryDao;

    /*  when all inputs are valid*/
    @Test
    public void testAddOrderUsingClientSkuIdsValid1() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId(productPojo.getClientSkuId()
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

        dto.addOrderUsingClientSkuIds(form);

        List<OrderPojo> listAfter = dao.selectAll();

        assertEquals(listBefore.size()+1, listAfter.size());
        assertEquals(form.getChannelOrderId(), dao.select(listAfter.get(listAfter.size()-1).getId()).getChannelOrderId());
        assertEquals(OrderStatus.CREATED, dao.select(listAfter.get(listAfter.size()-1).getId()).getOrderStatus());



    }

    /* when client SkuId is in upper case and have white spaces*/
    @Test
    public void testAddOrderUsingClientSkuIdValid2() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId("  CSKiUd1      "
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

        dto.addOrderUsingClientSkuIds(form);

        List<OrderPojo> listAfter = dao.selectAll();

        assertEquals(listBefore.size()+1, listAfter.size());
        assertEquals(form.getChannelOrderId(), dao.select(listAfter.get(listAfter.size()-1).getId()).getChannelOrderId());
        assertEquals(OrderStatus.CREATED, dao.select(listAfter.get(listAfter.size()-1).getId()).getOrderStatus());

    }
    /* when clientId or Customer Ids are invalid*/
    @Test
    public void testAddOrderUsingClientSkuIdInvalid1() throws ApiException {


        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId(productPojo.getClientSkuId()
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(7777777L, "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

        try {
            dto.addOrderUsingClientSkuIds(form);
            fail();
        }catch (ApiException e){
            assertEquals("Party with given id: "+ 7777777L+ " doesn't exist", e.getMessage());
        }



    }

    /* when channelOrderId is already present in DB*/
    @Test
    public void testAddOrderUsingClientSkuIdsInvalid2() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId(productPojo.getClientSkuId()
                , 40L, productPojo.getProductMrp());

        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

        dto.addOrderUsingClientSkuIds(form);
        try {
            dto.addOrderUsingClientSkuIds(form);
            fail();
        }catch(ApiException e){
            assertEquals("Channel Order ID: "+form.getChannelOrderId()+" already exists", e.getMessage());
        }

    }


    /*  when channelOrderId is empty*/
    @Test
    public void testAddOrderUsingClientSkuIdsInvalid3() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId(productPojo.getClientSkuId()
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "   "
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));


        try {
            dto.addOrderUsingClientSkuIds(form);
            fail();
        }catch (ApiException e){
            assertEquals("Channel Order ID must not be empty", e.getMessage());
        }

    }

    /* when client SKU ID(s) are empty*/
    @Test
    public void testAddOrderUsingClientSkuIdInvalid4() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId("    "
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

        try {
            dto.addOrderUsingClientSkuIds(form);
            fail();
        }catch (ApiException e){
            assertEquals("Client SKU ID(s) are empty at indexes: "+" ["+1+"]", e.getMessage());
        }



    }

    /* when duplicate ClientSkuIds are present in a CSV*/
    @Test
    public void testAddOrderUsingClientSkuIdsInvalid5() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId(productPojo.getClientSkuId()
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1, orderItems1}));


        try {
            dto.addOrderUsingClientSkuIds(form);
            fail();
        }catch (ApiException e){
            assertEquals("Client SKU ID(s) are being repeated at indexes: "+" ["+2+"]", e.getMessage());
        }

    }


    /*  when client SKU ID(s) are invalid*/
    @Test
    public void testAddOrderUsingClientSkuIdsInvalid6() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId("hdgsfhfhgdggdggdgdgdgdgdgdggdg"
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

        try {
            dto.addOrderUsingClientSkuIds(form);
            fail();
        }catch (ApiException e){
            assertEquals("Client SKU ID(s) don't exist"+" [\""+"hdgsfhfhgdggdggdgdgdgdgdgdggdg"+"\"]", e.getMessage());
        }


        }



        @Test
        public void testAllocateOrderValid1() throws ApiException {

            List<OrderPojo> listBefore = dao.selectAll();

            PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
            PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
            partyDao.insert(partyPojoClient);
            partyDao.insert(partyPojoCustomer);

            ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                    10.0 ,partyPojoClient.getPartyId());
            productDao.insert(productPojo);

            List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

            BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
            binSkuDao.insert(binSkuPojo);

            InventoryPojo inventoryPojo = createInventoryPojo(productPojo.getGlobalSkuId(), 100L,
                    0L, 0L);
            inventoryDao.insert(inventoryPojo);

            OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId(productPojo.getClientSkuId()
                    , 40L, productPojo.getProductMrp());
            OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                    , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

            dto.addOrderUsingClientSkuIds(form);

            List<OrderPojo> listAfter = dao.selectAll();

            dto.allocateOrder(listAfter.get(listAfter.size()-1).getId());

            assertEquals(OrderStatus.ALLOCATED, dao.select(listAfter.get(listAfter.size()-1).getId()).getOrderStatus());
            List<OrderItemPojo> orderItems = orderItemDao.selectByOrderId(listAfter.get(listAfter.size()-1).getId());
            assertEquals(Long.valueOf(40), orderItemDao.select(orderItems.get(orderItems.size()-1).getId()).getAllocatedQty());
    }

/* when Order is already allocated*/
    @Test
    public void testAllocateOrderInvalid1() throws ApiException {

        List<OrderPojo> listBefore = dao.selectAll();

        PartyPojo partyPojoClient = createMemberPojo("client1", Party.PartyType.CLIENT);
        PartyPojo partyPojoCustomer = createMemberPojo("cust1", Party.PartyType.CLIENT);
        partyDao.insert(partyPojoClient);
        partyDao.insert(partyPojoCustomer);

        ProductPojo productPojo = createProductPojo("p1", "b1", "cskiud1", "desc1",
                10.0 ,partyPojoClient.getPartyId());
        productDao.insert(productPojo);

        List<BinPojo> pojoList = binDao.insert(Arrays.asList(new BinPojo[]{new BinPojo()}));

        BinSkuPojo binSkuPojo = createBinSkuPojo(100L, productPojo.getGlobalSkuId(), pojoList.get(0).getBinId());
        binSkuDao.insert(binSkuPojo);

        InventoryPojo inventoryPojo = createInventoryPojo(productPojo.getGlobalSkuId(), 100L,
                0L, 0L);
        inventoryDao.insert(inventoryPojo);

        OrderItemWithClientSkuId orderItems1 = createOrderItemWithClientSkuId(productPojo.getClientSkuId()
                , 40L, productPojo.getProductMrp());
        OrderWithClientSkuIdForm form = createOrderWithClientSkuIdForm(partyPojoClient.getPartyId(), "abcbca"
                , partyPojoCustomer.getPartyId(), Arrays.asList(new OrderItemWithClientSkuId[]{orderItems1}));

        dto.addOrderUsingClientSkuIds(form);

        List<OrderPojo> listAfter = dao.selectAll();

        dto.allocateOrder(listAfter.get(listAfter.size()-1).getId());

        try{
            dto.allocateOrder(listAfter.get(listAfter.size()-1).getId());
            fail();
        }catch(ApiException e){
            assertEquals("Order already allocated", e.getMessage());
        }
    }




}
