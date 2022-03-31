//package com.increff.assure.dao;
//
//import com.increff.assure.pojo.PartyPojo;
//import com.increff.assure.spring.AbstractUnitTest;
//import com.increff.assure.spring.TestPojo;
//import com.increff.commons.Constants.Party;
//import com.increff.commons.Exception.ApiException;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//
//
//public class PartyDaoTest extends AbstractUnitTest {
//
//    @Autowired
//    private PartyDao dao;
//
//
//    @Test
//    public void testInsert() throws ApiException {
//        TestPojo testPojo = new TestPojo();
//        testPojo.preliminaryInsert();
//        List<PartyPojo> pojos_before = dao.selectAll();
//        PartyPojo partyPojo = getPartyPojo();
//        dao.insert(partyPojo);
//        List<PartyPojo> pojos_after = dao.selectAll();
//        assertEquals(pojos_before.size()+1, pojos_after.size());
//        assertEquals("amazon", dao.select(partyPojo.getPartyId()).getPartyName());
//    }
//
//    @Test
//    public void testSelectAll() throws ApiException {
//        TestPojo testPojo = new TestPojo();
//        testPojo.preliminaryInsert();
//        List<PartyPojo> pojos_before = dao.selectAll();
//        PartyPojo partyPojo = getPartyPojo();
//        dao.insert(partyPojo);
//        List<PartyPojo> pojos_after = dao.selectAll();
//        assertEquals(pojos_before.size()+1, pojos_after.size());
//
//    }
//
//    @Test
//    public void testSelect(){
//        PartyPojo partyPojo = getPartyPojo();
//        dao.insert(partyPojo);
//        assertEquals("amazon", dao.select(partyPojo.getPartyId()).getPartyName());
//        assertEquals(Party.PartyType.CLIENT, dao.select(partyPojo.getPartyId()).getPartyType());
//    }
//
//    @Test
//    public void testSelectByType(){
//        PartyPojo partyPojo = getPartyPojo();
//        dao.insert(partyPojo);
//        List<PartyPojo> list = dao.selectByType(Party.PartyType.CLIENT);
//        assertEquals(2, list.size());
//    }
//
//
//    private PartyPojo getPartyPojo() {
//        PartyPojo partyPojo = new PartyPojo();
//        partyPojo.setPartyName("amazon");
//        partyPojo.setPartyType(Party.PartyType.CLIENT);
//        return partyPojo;
//    }
//
//
//}
