package com.example.notificationService.service;

import com.example.notificationService.entity.Notification;
import com.example.notificationService.model.SendNotificationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    List<Notification> getAllUserById(String userId);

    void save(SendNotificationRequest notificationRequest);
}
