package com.example.juse.notification.dto;

import com.example.juse.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {

    private long notificationId;
    private long boardId;
    private String boardTitle;
    private long senderId;
    private String senderNickname;
    private long receiverId;
    private String receiverNickname;
    private Notification.Type type;
    private LocalDateTime createdAt;
    private boolean isRead;

}