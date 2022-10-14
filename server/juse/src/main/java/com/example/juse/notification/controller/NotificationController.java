package com.example.juse.notification.controller;


import com.example.juse.dto.MultiResponseDto;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.notification.dto.NotificationResponseDto;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.mapper.NotificationMapper;
import com.example.juse.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @RequestMapping("/main")
    public ResponseEntity<SingleResponseDto<? extends List<NotificationResponseDto>>> getNotifications() {

        List<Notification> notificationList = notificationService.getNotifications();

        List<NotificationResponseDto> response = notificationMapper.mapToNotificationResponseDtoList(notificationList);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

}
