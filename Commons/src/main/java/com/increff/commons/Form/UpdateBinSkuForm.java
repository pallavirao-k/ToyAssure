package com.increff.commons.Form;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBinSkuForm {
    @NotNull
    private Long binId;
    @NotNull
    private Long globalSkuId;
    @NotNull
    private Long qty;


}
