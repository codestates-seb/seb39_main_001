package com.example.juse.notification.slice;

import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.helper.stubservice.NotificationStub;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.repository.NotificationRepository;
import com.example.juse.notification.service.NotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Test
    @DisplayName("notifyApplicationAccepted")
    public void givenBoardAndApplication_thenNotificationIsSent() {

        Board mockedBoard = Mockito.mock(Board.class);

    }

    @Test
    @DisplayName("set all notifications as read")
    public void givenUserId_thenNotificationsSetRead() {

        long userId = 1L;
        NotificationStub notificationStub = new NotificationStub(userId, 6);
        List<Notification> notificationList = notificationStub.getNotificationStubList();


    }
}
