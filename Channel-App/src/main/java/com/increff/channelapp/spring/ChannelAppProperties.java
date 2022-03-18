package com.increff.channelapp.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ChannelAppProperties {

    @Value("${server.baseUrl}")
    private String baseUrl;


}

