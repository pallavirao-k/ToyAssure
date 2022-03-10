package com.increff.assure.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_PRODUCT;

@Entity
@Table(name="assureProduct", uniqueConstraints=@UniqueConstraint(name="unq_const_product", columnNames = {"clientSkuId", "clientId"}))
@Getter @Setter
public class ProductPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_PRODUCT)
    @TableGenerator(name=SEQ_PRODUCT, table=SEQ_PRODUCT)
    private Long globalSkuId;

    @Column(nullable = false)
    private String clientSkuId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String brandId;

    @Column(nullable = false)
    private Double productMrp;

    @Column(nullable = false)
    private String description;


}
