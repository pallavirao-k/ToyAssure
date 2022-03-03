package com.increff.commons.Form;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelListingForm {

    @NotNull
    private String channelSkuId;
    @NotNull
    private String clientSkuId;


}
