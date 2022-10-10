package com.example.juse.notification.controller;


import com.example.juse.notification.dto.NotificationResponse;
import com.example.juse.notification.dto.NotificationResponseBoardDto;
import com.example.juse.notification.dto.NotificationResponseLikeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class NotificationController {


    @RequestMapping("notification")
    public ResponseEntity<List<NotificationResponse>> get() {

        return new ResponseEntity<>(
                List.of(new NotificationResponseBoardDto(), new NotificationResponseLikeDto()),
                HttpStatus.OK);
    }

}
