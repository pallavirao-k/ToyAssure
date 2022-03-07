package com.increff.commons.Form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BinSkuForm {

    @NotNull
    private Long binId;
    @NotNull
    private String clientSkuId;
    @NotNull
    @Min(value = 1L)
    @Max(value = 5000L)
    private Long qty;
}
