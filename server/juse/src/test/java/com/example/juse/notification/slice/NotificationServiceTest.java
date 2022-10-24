package com.example.juse.notification.slice;

import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
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

}
