package com.increff.assure.pojo;

import com.increff.commons.Constants.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_ORDER;

@Entity
@Table(name="assureOrders", uniqueConstraints = @UniqueConstraint(name="unq_order", columnNames = {"channelId", "channelOrderId"}))
@Getter @Setter
public class OrderPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_ORDER)
    @TableGenerator(name=SEQ_ORDER, table=SEQ_ORDER)
    private Long id;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long channelId;

    @Column(nullable = false)
    private String channelOrderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status.ProductStatus productStatus;
}
