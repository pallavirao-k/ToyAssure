package com.increff.assure.service;

import com.increff.assure.dao.InvoiceDao;
import com.increff.assure.dto.InvoiceDto;
import com.increff.assure.pojo.InvoicePojo;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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


    public HashMap<InvoicePojo, byte[]> getcheckByOrderId(Long orderId) throws ApiException, IOException {
        InvoicePojo invoicePojo = dao.selectByOrderId(orderId);
        if(Objects.isNull(invoicePojo)){
            throw new ApiException("Invoice not generated with orderId: "+orderId);
        }
        Path pdfPath = Paths.get(invoicePojo.getInvoiceUrl());
        byte[] pdf = Files.readAllBytes(pdfPath);

        HashMap<InvoicePojo, byte[]> hm = new HashMap<>();
        hm.put(invoicePojo, pdf);
        return hm;
    }
}
