package com.increff.commons.Form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductForm {

    @NotNull
    private String productName;
    @NotNull
    private String brandId;
    @NotNull
    @Min(value = 1)
    @Max(value = 5000)
    private Double productMrp;
    @NotNull
    private String description;
}
