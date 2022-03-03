package com.increff.assure.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_ORDER_ITEM;

@Entity
@Table(name="assureOrderItem", uniqueConstraints = @UniqueConstraint(name="unq_order_item", columnNames = {"orderId", "globalSkuId"}))
@Getter @Setter
public class OrderItemPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_ORDER_ITEM)
    @TableGenerator(name=SEQ_ORDER_ITEM, table=SEQ_ORDER_ITEM)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long globalSkuId;

    @Column(nullable = false)
    private Long orderedQty;

    @Column(nullable = false)
    private Long allocatedQty;

    @Column(nullable = false)
    private Long fulfilledQty;

    @Column(nullable = false)
    private Double sellingPricePerUnit;
}
