package com.example.notificationService.service.impl;

import com.example.notificationService.entity.Notification;
import com.example.notificationService.model.SendNotificationRequest;
import com.example.notificationService.repository.NotificationRepository;
import com.example.notificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    @Override
    public List<Notification> getAllUserById(String userId) {
        return notificationRepository.findAllByUserIdOrderByCreationTimestampDesc(userId);
    }

    @Override
    public void save(SendNotificationRequest notificationRequest) {
        var notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .userId(notificationRequest.getUserId())
                .offerId(notificationRequest.getOfferId())
                .message(notificationRequest.getMessage())
                .build();
        notificationRepository.save(notification);
    }
}
