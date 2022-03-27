package com.increff.commons.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData {

    private Long id;
    private Long orderId;
    private Long globalSkuId;
    private Long orderedQty;
    private Long allocatedQty;
    private Long fulfilledQty;
    private Double sellingPricePerUnit;
}
