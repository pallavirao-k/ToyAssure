package com.increff.assure.service;

import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.commons.Constants.Invoice;
import com.increff.commons.Exception.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChannelServiceTest extends AbstractUnitTest {

    @Autowired
    private ChannelService channelService;

    @Test
    public void testAddChannel() throws ApiException {
        preliminaryInsert();
        List<ChannelPojo> list_before = channelService.getAllChannels();
        ChannelPojo channelPojo = getChannelPojo();
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = channelService.getAllChannels();

        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testGetChannel() throws ApiException {
        preliminaryInsert();
        List<ChannelPojo> list_before = channelService.getAllChannels();
        ChannelPojo channelPojo = getChannelPojo();
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = channelService.getAllChannels();

        assertEquals(list_after.get(list_after.size()-1).getChannelName(), channelPojo.getChannelName());
    }

    @Test
    public void testGetAllChannels() throws ApiException {
        preliminaryInsert();
        List<ChannelPojo> list_before = channelService.getAllChannels();
        ChannelPojo channelPojo = getChannelPojo();
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = channelService.getAllChannels();

        assertEquals(list_before.size()+1, list_after.size());
    }

    @Test
    public void testGetCheckByName() throws ApiException {
        preliminaryInsert();
        List<ChannelPojo> list_before = channelService.getAllChannels();
        ChannelPojo channelPojo = getChannelPojo();
        channelService.addChannel(channelPojo);
        List<ChannelPojo> list_after = channelService.getAllChannels();

        assertEquals(list_after.get(list_after.size()-1).getChannelName(), channelService.
                getCheckChannelByName(channelPojo.getChannelName()).getChannelName());
    }


    private ChannelPojo getChannelPojo(){
        ChannelPojo channelPojo = new ChannelPojo();
        channelPojo.setChannelName("channel2");
        channelPojo.setInvoiceType(Invoice.InvoiceType.CHANNEL);
        return channelPojo;
    }
}
