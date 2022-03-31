package com.increff.assure.dto;

import com.increff.assure.dao.ChannelDao;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.assure.spring.TestPojo;
import com.increff.commons.Constants.Invoice;
import com.increff.commons.Data.ChannelData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ChannelForm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChannelDtoTest extends AbstractUnitTest {

    @Autowired
    private ChannelDto dto;
    @Autowired
    private ChannelDao dao;

    /* when all the inputs are in lower case and without white spaces*/
    @Test
    public void testAddValid1() throws ApiException {
        List<ChannelPojo> data_before = dao.selectAll();

        ChannelForm form = getChannelForm();
        dto.add(form);

        List<ChannelPojo> data_after = dao.selectAll();

        assertEquals(data_before.size()+1, data_after.size());
        assertEquals("channel2", dao.select(data_after.get(data_after.size()-1).getId()).getChannelName());

    }

    /* when all the inputs are in Upper case and has white spaces*/
    @Test
    public void testAddValid2() throws ApiException {
        List<ChannelPojo> data_before = dao.selectAll();

        ChannelForm form = getChannelForm();
        form.setChannelName("   ChanneL2    ");
        dto.add(form);

        List<ChannelPojo> data_after = dao.selectAll();

        assertEquals(data_before.size()+1, data_after.size());
        assertEquals("channel2", dao.select(data_after.get(data_after.size()-1).getId()).getChannelName());
    }



    /* when name is empty and has white spaces */
    @Test
    public void testAddInvalid1() throws ApiException {
        List<ChannelPojo> data_before = dao.selectAll();

        ChannelForm form = getChannelForm();
        form.setChannelName("      ");
        try{
            dto.add(form);
            fail();
        }catch (ApiException e){
            assertEquals("Channel name must not be empty", e.getMessage());
        }
    }


    @Test
    public void testGet() throws ApiException {
        ChannelForm form = getChannelForm();
        form.setChannelName("   ChanneL2    ");
        dto.add(form);

        List<ChannelPojo> list = dao.selectAll();
        assertEquals("channel2", dto.getChannel(list.get(list.size()-1).getId()).getChannelName());

    }

    /* when ID is invalid */
    @Test
    public void testGetInvalid() throws ApiException {
        try{
            dto.getChannel(10000006L);
            fail();
        }catch (ApiException e){
            assertEquals("Channel doesn't exist with id: "+10000006L, e.getMessage());
        }

    }


    @Test
    public void testGetAllChannels() throws ApiException {

        List<ChannelData> data_before = dto.getAllChannels();
        ChannelForm form = getChannelForm();
        dto.add(form);
        List<ChannelData> data_after = dto.getAllChannels();

        assertEquals(data_before.size()+1, data_after.size());
    }




    private ChannelForm getChannelForm(){
        ChannelForm form = new ChannelForm();
        form.setInvoiceType(Invoice.InvoiceType.CHANNEL);
        form.setChannelName("channel2");
        return form;

    }
}
