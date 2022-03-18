package com.increff.assure.service;

import com.increff.assure.dao.InvoiceDao;
import com.increff.assure.dto.InvoiceDto;
import com.increff.assure.pojo.InvoicePojo;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InvoiceService {

    @Autowired
    private InvoiceDao dao;

    public InvoicePojo add(InvoicePojo invoicePojo){
        dao.insert(invoicePojo);
        return invoicePojo;
    }


    public InvoicePojo getcheckByOrderId(Long orderId) throws ApiException {
        InvoicePojo invoicePojo = dao.selectByOrderId(orderId);
        if(Objects.isNull(invoicePojo)){
            throw new ApiException("Invoice not generated with orderId: "+orderId);
        }
        return invoicePojo;
    }
}
