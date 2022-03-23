package com.increff.assure.spring;

import com.increff.commons.Constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
public class OrderSearchProperties {

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private Long channelId;
    private String channelOrderId;
    private OrderStatus orderStatus;
}
