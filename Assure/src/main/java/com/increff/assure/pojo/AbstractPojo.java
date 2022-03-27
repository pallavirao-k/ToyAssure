package com.increff.assure.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class AbstractPojo {

    private ZonedDateTime createdAt = ZonedDateTime.now();

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @NotNull
    private String version;


}
