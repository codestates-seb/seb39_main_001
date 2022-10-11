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
public class NotificationResponseBoardDto extends NotificationResponse {

    private long boardId;
    private String boardTitle;
    private long userId;
    private String nickname;
    private Notification.Type type;
    private LocalDateTime createdAt;
    private boolean isRead;

}