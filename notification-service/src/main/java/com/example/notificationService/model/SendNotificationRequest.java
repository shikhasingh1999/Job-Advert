package com.example.notificationService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendNotificationRequest {

    private String userId;
    private String offerId;
    private String message;
}
