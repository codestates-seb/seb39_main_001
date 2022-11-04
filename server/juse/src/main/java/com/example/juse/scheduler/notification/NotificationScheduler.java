package com.example.juse.scheduler.notification;

import com.example.juse.board.repository.BoardRepository;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.bookmark.repository.BookmarkRepository;
import com.example.juse.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationScheduler {

    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;
    private final NotificationService notificationService;

//    @Scheduled(cron = "0 * * * * MON-SUN")
    public void checkRemainingDaysOfBookmarkedBoards() {
        final LocalDate currentDate = LocalDate.now(Clock.systemDefaultZone());

        bookmarkRepository.findAll().stream()
                .filter(bookmark -> !bookmark.isNotified())
                .filter(bookmark -> Period.between(currentDate, bookmark.getBoard().getDueDate()).getDays() <= 3)
                .forEach(bookmark ->
                        {
                            bookmark.setNotified(true);
                            Bookmark savedBookmark = bookmarkRepository.save(bookmark);
                            notificationService.notifyRemainingDaysOfBookmarkedBoards(savedBookmark);
                            log.info("today : {} , due date :{}, remaining days: {}"
                                    , currentDate, bookmark.getBoard().getDueDate(), Period.between(currentDate, bookmark.getBoard().getDueDate()).getDays());
                        }

                );

    }

    @Scheduled(cron = "0 * * * * MON-SUN")
    public void updateOpenedBoardAsClosed() {
        notificationService.setBoardStatusAsClosed();
        log.info("boards are closed");
    }

}
