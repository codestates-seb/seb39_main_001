package com.example.juse.notification.slice;

import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.helper.stubservice.BoardStub;
import com.example.juse.helper.stubservice.NotificationStub;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.repository.NotificationRepository;
import com.example.juse.notification.service.NotificationService;
import com.example.juse.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    BoardRepository boardRepository;

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

    @Test
    public void whenBoardStatusSetAsClosed_thenNotificationIsSent() {

        Board mockedBoard = Mockito.mock(Board.class);
        List mockedList = Mockito.mock(List.class);
        User mockedUser = Mockito.mock(User.class);

        BoardStub boardStub = new BoardStub();
        boardStub.setApplicationsForBoard();
        Board board = boardStub.getBoard();
        List<Application> applicationList = board.getApplicationList();


        //given
        when(boardRepository.findCurrentlyOpened()).thenReturn(mockedList);
        when(mockedList.size()).thenReturn(1);
        when(mockedList.get(anyInt())).thenReturn(mockedBoard);
        when(mockedBoard.getApplicationList()).thenReturn(applicationList);

        when(mockedBoard.getUser()).thenReturn(mockedUser);


        //when
        notificationService.setBoardStatusAsClosed();

        //then
        verify(notificationRepository, atLeast(1)).save(any(Notification.class));
        verify(boardRepository).saveAll(mockedList);
    }
}
