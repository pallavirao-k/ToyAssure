package com.increff.commons.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponse {

    private Long id;
    private Long orderId;
    private String invoiceUrl;
    private byte[] b64Data;
}
