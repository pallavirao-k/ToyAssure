package com.increff.commons.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryData {

    private Long id;
    private Long globalSkuId;
    private Long availableQty = 0L;
    private Long allocatedQty=0L;
    private Long fulfilledQty=0L;
}
