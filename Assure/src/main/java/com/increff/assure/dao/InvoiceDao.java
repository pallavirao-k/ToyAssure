package com.increff.assure.dao;

import com.increff.assure.pojo.InvoicePojo;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class InvoiceDao extends GenericDao<InvoicePojo>{

    private static final String SELECT_BY_ORDER_ID = "SELECT p from InvoicePojo p WHERE orderID =: orderId";

    public InvoicePojo selectByOrderId(Long orderId){
        InvoicePojo invoicePojo;
        try{
        TypedQuery<InvoicePojo> q = getQuery(SELECT_BY_ORDER_ID, InvoicePojo.class);
        q.setParameter("orderId", orderId);
        invoicePojo = q.getSingleResult();
        }catch (NoResultException e){
            invoicePojo = null;
        }
        return invoicePojo;
    }


}
