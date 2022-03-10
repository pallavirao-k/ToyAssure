package com.increff.assure.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_BIN_SKU;

@Entity
@Table(name="assureBinSku", uniqueConstraints=@UniqueConstraint(name="unq_const_bin_sku", columnNames = {"binId", "globalSkuId"}))
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
