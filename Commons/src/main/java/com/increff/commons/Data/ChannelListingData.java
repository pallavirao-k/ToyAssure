package com.increff.commons.Data;

import com.increff.commons.Form.ChannelListingForm;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChannelListingData {

    private Long id;
    private Long channelId;
    private String channelSkuId;
    private Long clientId;
    private Long globalSkuId;

}
