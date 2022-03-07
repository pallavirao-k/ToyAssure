package com.increff.commons.Form;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm  {

    @NotNull
    private String clientSkuId;
    @NotNull
    private String productName;
    @NotNull
    private String brandId;
    @NotNull
    @Min(value = 1)
    private Double productMrp;
    @NotNull
    private String description;
}
