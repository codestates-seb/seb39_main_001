package com.example.juse.helper.stubservice;

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

    public BoardStub() {
        this.user = buildUserStub();
        this.board = buildPlainBoardStub();
        this.boardList = new ArrayList<>();
    }

    public BoardStub(LocalDate dueDate) {
        this();
        this.board.setDueDate(dueDate);
    }

    public BoardStub(int count, LocalDate dueDate) {
        this(dueDate);
        buildPlainBoardListStub(count, dueDate);
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

    private void buildPlainBoardListStub(int count, LocalDate dueDate) {

        for (int i = 0; i < count; i++) {
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


}
