package com.increff.commons.Data;

import com.increff.commons.Constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Getter
@Setter
public class OrderData {

    private Long id;
    private Long clientId;
    private Long customerId;
    private Long channelId;
    private String channelOrderId;
    private OrderStatus orderStatus;
}
