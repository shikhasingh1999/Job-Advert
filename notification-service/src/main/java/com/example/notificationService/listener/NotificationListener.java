package com.example.notificationService.listener;

import com.example.notificationService.model.SendNotificationRequest;
import com.example.notificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = {"${spring.kafka.topic.name}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consume (SendNotificationRequest notificationRequest) {
        log.info("Consumed message: {}", notificationRequest.toString());
        notificationService.save(notificationRequest);
    }
}
