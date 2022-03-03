package com.increff.commons.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderForm {

    @NotNull
    private String clientSkuId;
    @NotNull
    private Long qty;
    @NotNull
    private Double sellingPricePerUnit;
}
