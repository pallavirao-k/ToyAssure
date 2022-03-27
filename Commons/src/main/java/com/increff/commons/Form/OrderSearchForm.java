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

    private String startDate;

    private String endDate;

    private Long channelId;

    private OrderStatus orderStatus;

    private String channelOrderId;
}
