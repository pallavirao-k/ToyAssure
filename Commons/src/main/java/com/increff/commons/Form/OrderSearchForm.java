package com.increff.commons.Form;

import com.increff.commons.Constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
// change datatypes to their respective data types
public class OrderSearchForm {
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private Long channelId;
    @NotNull
    private OrderStatus orderStatus;
    @NotNull
    private String channelOrderId;
}
