package com.increff.assure.pojo;

import com.increff.commons.Constants.Invoice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_CHANNEL;

@Entity
@Table(name="assureChannel", indexes=@Index(name="channel_index", columnList = "channelName", unique = true))
@Getter @Setter
public class ChannelPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_CHANNEL)
    @TableGenerator(name=SEQ_CHANNEL, table=SEQ_CHANNEL)
    private Long id;

    @Column(nullable = false)
    private String channelName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Invoice.InvoiceType invoiceType;
}
