package com.increff.commons.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemWithChannelSkuId {

    @NotNull
    private String channelSkuId;
    @NotNull
    @Min(value=1L)
    @Max(value = 5000L)
    private Long qty;
    @NotNull
    @Min(value = 1)
    private Double sellingPricePerUnit;
}
