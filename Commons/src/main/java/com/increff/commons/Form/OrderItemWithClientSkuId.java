package com.increff.commons.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemWithClientSkuId {

    @NotNull
    private String clientSkuId;
    @NotNull
    @Min(value = 1)
    @Max(value = 5000)
    private Long qty;
    @NotNull
    @Min(value = 1)
    private Double sellingPricePerUnit;
}
