package com.increff.commons.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderWithClientSkuIdForm {

    @NotNull
    private Long clientId;
    @NotNull
    private String channelOrderId;
    @NotNull
    private Long customerId;
    @NotNull
    @Valid
    private List<OrderItemWithClientSkuId> formList;
}
