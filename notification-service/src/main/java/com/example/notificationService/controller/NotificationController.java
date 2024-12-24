package com.example.notificationService.controller;

import com.example.notificationService.dto.NotificationDto;
import com.example.notificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final ModelMapper modelMapper;

    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<List<NotificationDto>> getAllByUserId (@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getAllUserById(userId)
                .stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .toList());
    }
}
