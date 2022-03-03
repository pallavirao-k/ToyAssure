package com.increff.assure.dto;

import com.increff.assure.service.OrderService;
import com.increff.commons.Form.UploadOrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDto {

    @Autowired
    private OrderService service;

    public void add(UploadOrderForm uploadOrderForm){

    }
}
