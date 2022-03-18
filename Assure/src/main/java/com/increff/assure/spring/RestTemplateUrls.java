package com.increff.assure.spring;

public class RestTemplateUrls {

    public static String getChannelAppInvoiceUri(){
        return AssureAppProperties.channelBaseUrl+"api/invoice";
    }
}
