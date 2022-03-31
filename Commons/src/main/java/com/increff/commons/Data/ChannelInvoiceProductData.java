package com.increff.commons.Data;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelInvoiceProductData {

    @XmlElement(name="productName")
    private String productName;
    @XmlElement(name="channelSkuId")
    private String channelSkuId;
    @XmlElement(name="quantity")
    private Long qty;
    @XmlElement(name="sellingPricePerUnit")
    private Double sellingPricePerUnit;

}