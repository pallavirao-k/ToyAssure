package com.increff.assure.pojo;

import com.sun.istack.NotNull;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public class AbstractPojo {

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @NotNull
    private String version;


}
