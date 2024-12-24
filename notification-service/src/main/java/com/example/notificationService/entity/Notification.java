package com.example.notificationService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity(name = "notifications")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @UuidGenerator
    private String id;

    private String userId;
    private String offerId;
    private String message;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

}
