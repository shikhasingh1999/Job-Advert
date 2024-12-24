package com.example.notificationService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

    private String id;
    private String userId;
    private String offerId;
    private String message;
}
