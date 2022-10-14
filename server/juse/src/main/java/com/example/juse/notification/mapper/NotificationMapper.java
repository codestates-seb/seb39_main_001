package com.example.juse.notification.mapper;

import com.example.juse.notification.dto.NotificationResponseDto;
import com.example.juse.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "boardTitle", source = "board.title")
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderNickname", source = "sender.nickname")
    @Mapping(target = "receiverId", source = "receiver.id")
    @Mapping(target = "receiverNickname", source = "receiver.nickname")
    @Mapping(target = "isRead", source = "read")
    NotificationResponseDto mapToNotificationResponseDto(Notification notification);

    List<NotificationResponseDto> mapToNotificationResponseDtoList(List<Notification> notificationList);
}
