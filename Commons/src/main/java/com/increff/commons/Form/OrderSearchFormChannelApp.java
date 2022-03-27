package com.increff.commons.Form;

import com.increff.commons.Constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderSearchFormChannelApp {
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private Long channelId;
    @NotNull
    private String channelOrderId;
}
