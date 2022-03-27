package com.increff.assure.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class AssureAppProperties {

    @Value("${channel.serverUri}")
    public String channelBaseUrl;
}
