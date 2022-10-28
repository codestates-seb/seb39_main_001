package com.example.juse.helper.stubservice;

import com.example.juse.board.entity.Board;
import com.example.juse.notification.entity.Notification;
import com.example.juse.user.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationStub {

    private final Notification notification;
    private final List<Notification> notificationList;

    private final User user;

    public NotificationStub(long userId, long count) {
        this.user = buildUserStub(userId);
        this.notification = buildNotificationStub();
        this.notificationList = buildNotificationListStub(count);
    }

    public Notification getNotificationStub() {
        return this.notification;
    }

    public List<Notification> getNotificationStubList() {
        return this.notificationList;
    }

    private Notification buildNotificationStub() {
        return Notification.builder()
                .id(1L)
                .receiver(user)
                .board(Board.builder().id(1L).build())
                .build();
    }

    private List<Notification> buildNotificationListStub(long count) {

        List<Board> boardList = new ArrayList<>();
        List<Notification> notificationList = new ArrayList<>();

        for (long i = 1; i <= count; i++) {
            boardList.add(buildBoardStub(i));
        }

        for (long i = 1; i <= boardList.size(); i++) {
            notificationList.add(
                    Notification.builder()
                            .id(i)
                            .receiver(user)
                            .board(boardList.get((int) (i-1L)))
                            .build()
            );
        }

        return notificationList;
    }

    private User buildUserStub(long userId) {
        return User.builder()
                .id(userId)
                .nickname("stubbed user 1")
                .email("stub@gmail.com")
                .introduction("stub")
                .build();
    }

    private Board buildBoardStub(long boardId) {
        return Board.builder()
                .id(boardId)
                .title("board" + boardId)
                .user(user)
                .content("board stub" + boardId)
                .backend(5)
                .frontend(2)
                .designer(1)
                .etc(2)
                .people(10)
                .contact("contact")
                .dueDate(LocalDate.now())
                .startingDate(LocalDate.of(2022, 10, 5))
                .period("3")
                .onOffline("online")
                .status(Board.Status.OPENING)
                .type(Board.Type.PROJECT)
                .build();
    }
}
