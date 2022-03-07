package com.increff.commons.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UploadBinSkuForm {

    @NotNull
    private Long clientId;
    @NotNull
    @Valid
    private List<BinSkuForm> formList;

}
