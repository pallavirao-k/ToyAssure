package com.increff.commons.Form;


import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductForm  {

    @NotNull
    private String clientSkuId;
    @NotNull
    private String productName;
    @NotNull
    private String brandId;
    @NotNull
    private Double productMrp;
    @NotNull
    private String description;
}
