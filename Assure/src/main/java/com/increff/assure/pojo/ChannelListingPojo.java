package com.increff.assure.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_CHANNEL_LISTING;

@Entity
@Table(name="assureChannelListings", indexes=@Index(name="", columnList = "channelId, channelSkuId, clientId", unique=true), uniqueConstraints = @UniqueConstraint(columnNames = "globalSkuId"))
@Getter
@Setter
public class ChannelListingPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_CHANNEL_LISTING)
    @TableGenerator(name=SEQ_CHANNEL_LISTING, table=SEQ_CHANNEL_LISTING)
    private Long id;

    @Column(nullable = false)
    private Long channelId;

    @Column(nullable = false)
    private String channelSkuId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private Long globalSkuId;
}
