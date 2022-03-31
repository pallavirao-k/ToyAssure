package com.increff.assure.service;

import com.increff.assure.dao.PartyDao;
import com.increff.assure.pojo.PartyPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.assure.spring.TestPojo;
import com.increff.commons.Constants.Party;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.PartyForm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.assure.spring.TestPojo.createMemberPojo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PartyServiceTest extends AbstractUnitTest {

    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyDao dao;

    @Test
    public void testAdd() throws ApiException {
        List<PartyPojo> data_before = dao.selectAll();
        PartyPojo partyPojo = createMemberPojo("name1", Party.PartyType.CLIENT);
        partyService.add(partyPojo);
        List<PartyPojo> data_after = dao.selectAll();

        assertEquals(data_before.size()+1, data_after.size());
        assertEquals("name1", dao.select(partyPojo.getPartyId()).getPartyName());
    }

    /* when combination of name anf type is already present in DB*/
    @Test
    public void testAddInvalid1() throws ApiException {
        List<PartyPojo> data_before = dao.selectAll();
        PartyPojo partyPojo = createMemberPojo("name1", Party.PartyType.CLIENT);
        partyService.add(partyPojo);
        partyPojo = createMemberPojo("name1", Party.PartyType.CLIENT);
        try {
            partyService.add(partyPojo);
            fail();
        }catch (ApiException e){
            assertEquals(partyPojo.getPartyType()+" with name: "+partyPojo.getPartyName()+" already exists"
            , e.getMessage());
        }

    }

    @Test
    public void testGetAll() throws ApiException {
        List<PartyPojo> data_before = dao.selectAll();
        PartyPojo partyPojo = createMemberPojo("name1", Party.PartyType.CLIENT);
        partyService.add(partyPojo);
        List<PartyPojo> data_after = partyService.getAll();

        assertEquals(data_before.size()+1, data_after.size());

    }

    @Test
    public void testGet() throws ApiException {
        List<PartyPojo> data_before = dao.selectAll();
        PartyPojo partyPojo = createMemberPojo("name1", Party.PartyType.CLIENT);
        partyService.add(partyPojo);
        List<PartyPojo> data_after = dao.selectAll();

        assertEquals(data_before.size()+1, data_after.size());
        assertEquals("name1", partyService.get(partyPojo.getPartyId()).getPartyName());
    }

    @Test
    public void testGetByType() throws ApiException {

        List<PartyPojo> list_before = partyService.getPartyByType(Party.PartyType.CLIENT);
        PartyPojo partyPojo = createMemberPojo("name1", Party.PartyType.CLIENT);
        partyService.add(partyPojo);
        List<PartyPojo> list_after = partyService.getPartyByType(Party.PartyType.CLIENT);
        assertEquals(list_before.size()+1, list_after.size());
    }


}
