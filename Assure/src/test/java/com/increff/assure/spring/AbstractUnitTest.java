package com.increff.assure.spring;



import com.increff.assure.pojo.*;
import com.increff.assure.service.*;
import com.increff.assure.spring.SpringTestConfig;
import com.increff.commons.Constants.Invoice;
import com.increff.commons.Constants.OrderStatus;
import com.increff.commons.Constants.Party;
import com.increff.commons.Exception.ApiException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration("src/test/webapp")
@Transactional
public abstract class AbstractUnitTest {

    @Autowired
    private PartyService partyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BinService binService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelListingService channelListingService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderService orderService;


    public void preliminaryInsert() throws ApiException {
        PartyPojo partyPojo = new PartyPojo();
        partyPojo.setPartyName("name");
        partyPojo.setPartyType(Party.PartyType.CLIENT);
        partyService.add(partyPojo);

        PartyPojo partyPojo1 = new PartyPojo();
        partyPojo1.setPartyName("name1");
        partyPojo1.setPartyType(Party.PartyType.CUSTOMER);
        partyService.add(partyPojo1);

        List<ProductPojo> productPojoList = new ArrayList<>();
        ProductPojo productPojo = new ProductPojo();
        productPojo.setProductName("air-force1");
        productPojo.setBrandId("bi1");
        productPojo.setProductMrp(10.0);
        productPojo.setClientId(1L);
        productPojo.setClientSkuId("cskuid1");
        productPojo.setDescription("desc1");
        productPojoList.add(productPojo);
        productService.add(partyPojo.getPartyId(), productPojoList);

        List<BinPojo> binPojos = new ArrayList<>();
        for(int i = 0; i<5;i++){
            BinPojo binPojo = new BinPojo();
            binPojos.add(binPojo);
        }
        binService.add(binPojos);

        BinSkuPojo binSkuPojo = new BinSkuPojo();
        binSkuPojo.setQty(100L);
        binSkuPojo.setBinId(binPojos.get(0).getBinId());
        binSkuPojo.setGlobalSkuId(productPojoList.get(0).getGlobalSkuId());
        binService.addBinSku(binSkuPojo);

        ChannelPojo channelPojo = new ChannelPojo();
        channelPojo.setChannelName("channel1");
        channelPojo.setInvoiceType(Invoice.InvoiceType.CHANNEL);
        channelService.addChannel(channelPojo);

        ChannelListingPojo channelListingPojo = new ChannelListingPojo();
        channelListingPojo.setChannelId(channelPojo.getId());
        channelListingPojo.setChannelSkuId("chskuId1");
        channelListingPojo.setGlobalSkuId(productPojo.getGlobalSkuId());
        channelListingPojo.setClientId(partyPojo.getPartyId());
        List<ChannelListingPojo> channelListingPojos = new ArrayList<>();
        channelListingPojos.add(channelListingPojo);
        channelListingService.addChannelListings(channelListingPojos);

        inventoryService.addInventory(binSkuPojo);

        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setClientId(partyPojo.getPartyId());
        orderPojo.setOrderStatus(OrderStatus.CREATED);
        orderPojo.setChannelId(channelPojo.getId());
        orderPojo.setChannelOrderId("cOId1");
        orderPojo.setCustomerId(partyPojo1.getPartyId());
        orderService.addOrder(orderPojo);

        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderPojo.getId());
        orderItemPojo.setOrderedQty(10L);
        orderItemPojo.setGlobalSkuId(productPojo.getGlobalSkuId());
        orderItemPojo.setAllocatedQty(0L);
        orderItemPojo.setFulfilledQty(0L);
        orderItemPojo.setSellingPricePerUnit(productPojo.getProductMrp());
        orderService.insertOrderItem(orderItemPojo);

    }

}
