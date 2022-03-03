package com.increff.commons.Form;

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
    private Long qty;
}
