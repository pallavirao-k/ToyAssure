package com.increff.commons.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderWithChannelSkuIdForm {

    @NotNull
    private Long channelId;
    @NotNull
    private Long clientId;
    @NotNull
    private Long customerId;
    @NotNull
    private String channelOrderId;
    @NotNull
    @Valid
    private List<OrderItemWithChannelSkuId> orderItemWithChannelSkuIdList;
}
