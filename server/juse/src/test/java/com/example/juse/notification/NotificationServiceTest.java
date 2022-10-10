package com.example.juse.notification;

import com.example.juse.application.entity.Application;
import com.example.juse.application.repository.ApplicationRepository;
import com.example.juse.application.service.ApplicationService;
import com.example.juse.application.service.ApplicationServiceImpl;
import com.example.juse.board.entity.Board;
import com.example.juse.board.service.BoardService;
import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.notification.service.NotificationService;
import com.example.juse.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    NotificationService notificationService;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    BoardService boardService;

    @Test
    public void whenUserAppliedPosition_thenNotificationSentToWriter() throws CustomRuntimeException{
        Application mockedApplication = Mockito.mock(Application.class);
        User mockedUser = Mockito.mock(User.class);
        Board mockedBoard = Mockito.mock(Board.class);
        ApplicationServiceImpl mockedService = Mockito.mock(ApplicationServiceImpl.class);

        //given
        given(mockedApplication.getUser()).willReturn(mockedUser);
        given(mockedUser.getId()).willReturn(1L);
        given(mockedApplication.getBoard()).willReturn(mockedBoard);
        given(mockedBoard.getId()).willReturn(1L);
        when(mockedBoard.isPositionAvailable(anyString())).thenReturn(Boolean.TRUE);
        given(boardService.verifyBoardById(anyLong())).willReturn(mockedBoard);
        given(mockedApplication.getPosition()).willReturn("");
        given(applicationRepository.save(mockedApplication)).willReturn(mockedApplication);

        //when
        applicationService.create(mockedApplication);

        //then
        verify(notificationService).notifyNewApplication(mockedBoard, mockedApplication);

    }
}
