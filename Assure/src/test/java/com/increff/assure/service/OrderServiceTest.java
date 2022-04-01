package com.increff.assure.service;

import com.increff.assure.dao.*;
import com.increff.assure.pojo.*;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.assure.spring.TestPojo;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Constants.Party;
import com.increff.commons.Exception.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.increff.assure.spring.TestPojo.*;
import static org.junit.Assert.assertEquals;

public class OrderServiceTest extends AbstractUnitTest {

    @Autowired
    private OrderService orderService;

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


    @Test
    public void testAddOrder() throws ApiException {
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

        ChannelPojo channelPojo = channelDao.selectByName("internal");

        OrderPojo pojo = createOrderPojo(OrderStatus.CREATED, channelPojo.getId(), "ididid"
                , partyPojoCustomer.getPartyId(), partyPojoClient.getPartyId());

        orderService.addOrder(pojo);
        List<OrderPojo> listAfter = dao.selectAll();

        assertEquals(listBefore.size()+1, listAfter.size());

    }


    @Test
    public void testInsertOrderItemPojo() throws ApiException {

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

        ChannelPojo channelPojo = channelDao.selectByName("internal");

        OrderPojo pojo = createOrderPojo(OrderStatus.CREATED, channelPojo.getId(), "ididid"
                , partyPojoCustomer.getPartyId(), partyPojoClient.getPartyId());

        orderService.addOrder(pojo);

        List<OrderItemPojo> listBefore = orderItemDao.selectAll();

        OrderItemPojo orderItemPojo = createOrderItemPojo(pojo.getId(), productPojo.getGlobalSkuId(), 10L,
                0L, 0L, 10.0);
        orderService.insertOrderItem(orderItemPojo);
        List<OrderItemPojo> listAfter = orderItemDao.selectAll();

        assertEquals(listBefore.size()+1, listAfter.size());

    }



    @Test
    public void testGetByChannelIdAndChannelOrderId() throws ApiException {

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

        ChannelPojo channelPojo = channelDao.selectByName("internal");

        OrderPojo pojo = createOrderPojo(OrderStatus.CREATED, channelPojo.getId(), "ididid"
                , partyPojoCustomer.getPartyId(), partyPojoClient.getPartyId());

        orderService.addOrder(pojo);



        assertEquals(pojo.getId(), orderService.getByChannelIdAndChannelOrderId(channelPojo.getId(),
                "ididid").getId());
        assertEquals(OrderStatus.CREATED, orderService.getByChannelIdAndChannelOrderId(channelPojo.getId(),
                "ididid").getOrderStatus());

    }

    @Test
    public void testAllocateOrder() throws ApiException {
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

        ChannelPojo channelPojo = channelDao.selectByName("internal");

        OrderPojo pojo = createOrderPojo(OrderStatus.CREATED, channelPojo.getId(), "ididid"
                , partyPojoCustomer.getPartyId(), partyPojoClient.getPartyId());

        orderService.addOrder(pojo);

        OrderItemPojo orderItemPojo = createOrderItemPojo(pojo.getId(), productPojo.getGlobalSkuId(), 10L,
                0L, 0L, 10.0);
        orderItemDao.insert(orderItemPojo);

        InventoryPojo inventoryPojo = createInventoryPojo(productPojo.getGlobalSkuId(),
                100L, 0L, 0L);
        inventoryDao.insert(inventoryPojo);

        orderService.allocateOrder(orderItemPojo.getId(), 10L);

        assertEquals(Long.valueOf(10), orderItemPojo.getAllocatedQty());

    }

    @Test
    public void testChangeStatusToAllocated() throws ApiException {
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

        ChannelPojo channelPojo = channelDao.selectByName("internal");

        OrderPojo pojo = createOrderPojo(OrderStatus.CREATED, channelPojo.getId(), "ididid"
                , partyPojoCustomer.getPartyId(), partyPojoClient.getPartyId());

        orderService.addOrder(pojo);
        orderService.changeStatusToAllocated(pojo.getId());

        assertEquals(OrderStatus.ALLOCATED, pojo.getOrderStatus());

    }



}
