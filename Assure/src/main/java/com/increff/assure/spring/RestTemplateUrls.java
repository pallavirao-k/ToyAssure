package com.increff.assure.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class RestTemplateUrls {

    @Autowired
    AssureAppProperties properties;

    public String getChannelAppInvoiceUri(){
        return properties.getChannelBaseUrl()+"invoice";
    }
}
