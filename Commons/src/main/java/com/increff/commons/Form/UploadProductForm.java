package com.increff.commons.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UploadProductForm {

    @NotNull
    private Long clientId;
    @NotNull
    @Valid
    private List<ProductForm> formList;

}
