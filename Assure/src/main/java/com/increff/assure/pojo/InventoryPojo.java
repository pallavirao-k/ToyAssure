package com.increff.assure.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_INVENTORY;

@Entity
@Table(name="assureInventory", indexes=@Index(name="inventory_index", columnList = "globalSkuId", unique = true))
@Getter @Setter
public class InventoryPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_INVENTORY)
    @TableGenerator(name=SEQ_INVENTORY, table=SEQ_INVENTORY)
    private Long id;

    @Column(nullable = false)
    private Long globalSkuId;

    @Column(nullable = false)
    private Long availableQty;

    @Column(nullable = false)
    private Long allocatedQty =0L;

    @Column(nullable = false)
    private Long fulfilledQty = 0L;
}
