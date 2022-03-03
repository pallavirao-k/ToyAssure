package com.increff.commons.Data;

import com.increff.commons.Form.ProductForm;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductData extends ProductForm {

    private Long globalSkuId;
    private Long clientId;
}
