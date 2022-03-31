package com.increff.assure.service;

import com.increff.assure.dao.ChannelDao;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.assure.spring.TestPojo;
import com.increff.commons.Constants.Invoice;
import com.increff.commons.Exception.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.assure.spring.TestPojo.createChannelPojo;
import static org.junit.Assert.assertEquals;

public class ChannelServiceTest extends AbstractUnitTest {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelDao dao;

    /* when all inputs are valid*/

    @Test
    public void testAddChannelValid1() throws ApiException {

        List<ChannelPojo> list_before = dao.selectAll();
        ChannelPojo channelPojo = createChannelPojo("c1", Invoice.InvoiceType.CHANNEL);
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = dao.selectAll();

        assertEquals(list_before.size()+1, list_after.size());
        assertEquals("c1", dao.select(list_after.get(list_after.size()-1).getId()).getChannelName());
    }

    /* when all inputs are valid and are in Upper case as well and have white spaces*/

    @Test
    public void testAddChannelValid2() throws ApiException {

        List<ChannelPojo> list_before = dao.selectAll();
        ChannelPojo channelPojo = createChannelPojo("    C1    ", Invoice.InvoiceType.CHANNEL);
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = dao.selectAll();

        assertEquals(list_before.size()+1, list_after.size());
        assertEquals("c1", dao.select(list_after.get(list_after.size()-1).getId()).getChannelName());
    }

    @Test
    public void testGetChannel() throws ApiException {

        ChannelPojo channelPojo = createChannelPojo("c1", Invoice.InvoiceType.CHANNEL);
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = dao.selectAll();

        assertEquals(list_after.get(list_after.size()-1).getChannelName(), channelPojo.getChannelName());
    }

    @Test
    public void testGetAllChannels() throws ApiException {

        List<ChannelPojo> list_before = dao.selectAll();
        ChannelPojo channelPojo = createChannelPojo("c1", Invoice.InvoiceType.CHANNEL);
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = dao.selectAll();

        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testGetCheckByName() throws ApiException {

        ChannelPojo channelPojo = createChannelPojo("c1", Invoice.InvoiceType.CHANNEL);
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = dao.selectAll();

        assertEquals(list_after.get(list_after.size()-1).getChannelName(), channelService.
                getCheckChannelByName(channelPojo.getChannelName()).getChannelName());
    }


}
