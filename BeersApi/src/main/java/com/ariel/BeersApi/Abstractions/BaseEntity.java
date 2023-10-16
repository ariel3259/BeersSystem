package com.ariel.BeersApi.Abstractions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id() @GeneratedValue(strategy = GenerationType.AUTO )
    protected int id;

    @CreatedDate
    @Column(name = "created_at")
    protected Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected Date updatedAt;

    /*@CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    protected String updatedBy;
    */
    @Column
    @Getter
    protected boolean status;

}
