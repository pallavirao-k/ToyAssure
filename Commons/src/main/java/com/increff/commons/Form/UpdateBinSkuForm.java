package com.increff.commons.Form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import static com.increff.commons.Constants.ConstantNames.SEQ_MAX_VAL;
import static com.increff.commons.Constants.ConstantNames.SEQ_MIN_VAL;

@Getter
@Setter
public class UpdateBinSkuForm {
    @NotNull
    private Long binId;
    @NotNull
    private Long globalSkuId;
    @NotNull
    @Min(value = 1L)
    @Max(value = 5000L)
    private Long qty;


}
