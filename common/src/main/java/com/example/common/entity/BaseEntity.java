package com.example.common.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime updateTimestamp;
}

// also
//    @Id
//    @UuidGenerator
//    private String id;

// @GeneratedValue -> is from JPA API (decouples your code from a specific implementation, so you could change Hibernate to e.g. EclipseLink later)
// @UuidGenerator -> is from hibernate-core (couples code to Hibernate, which is one implementation of JPA)



