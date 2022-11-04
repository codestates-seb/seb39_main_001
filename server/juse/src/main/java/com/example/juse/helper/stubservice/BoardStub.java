package com.example.juse.helper.stubservice;

import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardStub {

    private final Board board;
    private final List<Board> boardList;
    private final User user;

    private final List<User> userList;
    private final List<Application> applicationList;

    public BoardStub() {
        this.user = buildUserStub();
        this.board = buildPlainBoardStub();
        this.boardList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.applicationList = new ArrayList<>();
    }

    public BoardStub(LocalDate dueDate) {
        this();
        this.board.setDueDate(dueDate);
    }

    public BoardStub(int numberOfBoards, LocalDate dueDate) {
        this(dueDate);
        buildPlainBoardListStub(numberOfBoards, dueDate);
    }

    private Board buildPlainBoardStub() {
        return Board.builder()
                .id(1L)
                .title("stub")
                .content("board stub")
                .user(this.user)
                .contact("random")
                .backend(1)
                .frontend(1)
                .designer(1)
                .etc(1)
                .startingDate(LocalDate.of(2022, 10, 10))
                .dueDate(LocalDate.now())
                .period("3")
                .onOffline("online")
                .type(Board.Type.PROJECT)
                .build();
    }

    private void buildPlainBoardListStub(int numberOfBoards, LocalDate dueDate) {

        for (int i = 0; i < numberOfBoards; i++) {
            Board stub = buildPlainBoardStub();
            stub.setId((long) i + 1);
            stub.setDueDate(dueDate.plusDays(i));
            this.boardList.add(stub);
        }

    }

    private User buildUserStub() {
        return User.builder()
                .id(1L)
                .introduction("user1 introduction")
                .nickname("user stub1")
                .email("user1")
                .build();
    }

    public void setApplicationsForBoard() {

        for (int i = 1; i <= 5; i++) {
            User user = buildUserStub();
            user.setId((long) i);
            user.setIntroduction("introduction" + i);

            Application application = Application
                    .builder()
                    .board(this.board)
                    .id((long) i)
                    .user(user)
                    .position("backend")
                    .build();

            applicationList.add(application);
        }

        this.board.setApplicationList(applicationList);

    }

}
