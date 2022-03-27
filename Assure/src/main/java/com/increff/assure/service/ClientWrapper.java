package com.increff.assure.service;

import com.increff.assure.spring.RestTemplateUrls;
import com.increff.commons.Data.InvoiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientWrapper {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateUrls restTemplateUrls;

    public String postForInvoiceInChannelApp(InvoiceData data){
        return restTemplate.postForObject(restTemplateUrls.getChannelAppInvoiceUri(), data, String.class);
    }
}
