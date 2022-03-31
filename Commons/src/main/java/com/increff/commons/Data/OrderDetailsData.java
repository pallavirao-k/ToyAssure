package com.increff.commons.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsData {

    private Long orderId;
    private String clientSkuId;
    private String brandId;
    private Long orderedQty;
    private Long allocatedQty;
    private Long fulfilledQty;
    private Double sellingPricePerUnit;
}
