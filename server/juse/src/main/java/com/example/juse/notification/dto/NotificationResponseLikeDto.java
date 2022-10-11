package com.example.juse.notification.dto;

import com.example.juse.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseLikeDto extends NotificationResponse {

    private Long userId;
    private String userNickname;
    private Notification.Type type = Notification.Type.LIKE;
    private LocalDateTime createdAt;
    private boolean isRead;

}