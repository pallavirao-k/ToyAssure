//package com.increff.channelapp.spring;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class RestTemplateUrls {
//    @Autowired
//    private ChannelAppProperties channelAppProperties;
//
//    public String getOrderUri(){
//        ChannelAppProperties channelAppProperties = new ChannelAppProperties();
//        return channelAppProperties.getServerBaseUrl()+"/api/orders";
//    }
//}
