package com.increff.assure.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.increff.commons.Constants.ConstantNames.SEQ_BIN;

@Entity
@Table(name="assureBin")
@Getter @Setter
public class BinPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = SEQ_BIN)
    @TableGenerator(name= SEQ_BIN, table=SEQ_BIN , initialValue = 1000)
    private Long binId;
}
