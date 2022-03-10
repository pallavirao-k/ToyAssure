package com.increff.commons.Data;

import com.increff.commons.Constants.OrderStatus;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class OrderData {

    private Long id;
    private Long clientId;
    private Long customerId;
    private Long channelId;
    private String channelOrderId;
    private OrderStatus orderStatus;
}
