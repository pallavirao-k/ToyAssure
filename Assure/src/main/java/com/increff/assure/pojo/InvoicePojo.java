package com.increff.assure.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_INVENTORY;
import static com.increff.commons.Constants.ConstantNames.SEQ_INVOICE;

@Entity
@Table(name="assureInventory", indexes=@Index(name="idx_invoice", columnList = "globalSkuId"))
@Getter
@Setter
public class InvoicePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_INVOICE)
    @TableGenerator(name=SEQ_INVOICE, table=SEQ_INVOICE)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String invoiceUrl;

}
