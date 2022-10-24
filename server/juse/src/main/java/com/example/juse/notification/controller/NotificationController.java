package com.example.juse.notification.controller;


import com.example.juse.dto.SingleResponseDto;
import com.example.juse.exception.validator.NotEmptyToken;
import com.example.juse.notification.dto.NotificationResponseDto;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.mapper.NotificationMapper;
import com.example.juse.notification.service.NotificationService;
import com.example.juse.security.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notifications")
@Controller
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;


    @GetMapping("/{notification-id}")
    public String redirect(@PathVariable("notification-id") long notificationId) {

        Notification notification = notificationService.getNotificationById(notificationId);

        return "redirect:/boards/" + notification.getBoard().getId();
    }
}
