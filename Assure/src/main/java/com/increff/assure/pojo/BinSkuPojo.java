package com.increff.assure.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_BIN_SKU;

@Entity
@Table(name="assureBinSku", indexes=@Index(name="bin_sku_index", columnList = "binId, globalSkuId", unique = true))
@Getter
@Setter
public class BinSkuPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_BIN_SKU)
    @TableGenerator(name=SEQ_BIN_SKU, table=SEQ_BIN_SKU)
    private Long id;

    @Column(nullable = false)
    private Long binId;

    @Column(nullable = false)
    private Long globalSkuId;

    @Column(nullable = false)
    private Long qty;
}
