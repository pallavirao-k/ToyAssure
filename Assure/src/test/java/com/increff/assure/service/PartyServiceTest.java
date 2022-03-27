package com.increff.assure.service;

import com.increff.assure.pojo.PartyPojo;
import com.increff.assure.spring.AbstractUnitTest;
import com.increff.commons.Constants.Party;
import com.increff.commons.Exception.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PartyServiceTest extends AbstractUnitTest {

    @Autowired
    private PartyService partyService;

    @Test
    public void testAdd() throws ApiException {
        preliminaryInsert();
        List<PartyPojo> pojos_before = partyService.getAll();
        PartyPojo partyPojo = getPartyPojo();
        partyService.add(partyPojo);
        List<PartyPojo> pojos_after = partyService.getAll();
        assertEquals(pojos_before.size()+1, pojos_after.size());
        assertEquals("amazon", partyService.get(partyPojo.getPartyId()).getPartyName());
    }

    @Test
    public void testGetAll() throws ApiException {
        preliminaryInsert();
        List<PartyPojo> pojos_before = partyService.getAll();
        PartyPojo partyPojo = getPartyPojo();
        partyService.add(partyPojo);
        List<PartyPojo> pojos_after = partyService.getAll();
        assertEquals(pojos_before.size()+1, pojos_after.size());

    }

    @Test
    public void testGet() throws ApiException {
        PartyPojo partyPojo = getPartyPojo();
        partyService.add(partyPojo);
        assertEquals("amazon", partyService.get(partyPojo.getPartyId()).getPartyName());
        assertEquals(Party.PartyType.CLIENT, partyService.get(partyPojo.getPartyId()).getPartyType());
    }

    @Test
    public void testGetByType() throws ApiException {
        preliminaryInsert();
        List<PartyPojo> list_before = partyService.getPartyByType(Party.PartyType.CLIENT);
        PartyPojo partyPojo = getPartyPojo();
        partyService.add(partyPojo);
        List<PartyPojo> list_after = partyService.getPartyByType(Party.PartyType.CLIENT);
        assertEquals(list_before.size()+1, list_after.size());
    }


    private PartyPojo getPartyPojo() {
        PartyPojo partyPojo = new PartyPojo();
        partyPojo.setPartyName("amazon");
        partyPojo.setPartyType(Party.PartyType.CLIENT);
        return partyPojo;
    }
}
