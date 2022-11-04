package com.example.juse.scheduler;

import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.bookmark.repository.BookmarkRepository;
import com.example.juse.notification.service.NotificationService;
import com.example.juse.scheduler.notification.NotificationScheduler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerTest {

    @InjectMocks
    NotificationScheduler notificationScheduler;

    @Mock
    BoardRepository boardRepository;

    @Mock
    BookmarkRepository bookmarkRepository;

    @Mock
    NotificationService notificationService;

    @Test
    @DisplayName("set board status as close test")
    public void whenBoardsAreOpenedToTheDay_thenSetBoardStatusAsClosed() {

        //when
        notificationScheduler.updateOpenedBoardAsClosed();

        verify(notificationService).setBoardStatusAsClosed();
    }
}
