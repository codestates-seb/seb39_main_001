package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import com.example.juse.user.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class BoardServiceStub implements BoardSerivice{

    private final User user1 =
            User.builder()
                    .id(1L)
                    .introduction("user1")
                    .email("user1")
                    .portfolio("github.com/user1")
                    .nickname("user mouth")
                    .build();

    private final Board board1 =
            Board.builder()
                    .id(1L)
                    .content("content1")
                    .backend(5)
                    .frontend(2)
                    .designer(1)
                    .etc(2)
                    .people(10)
                    .contact("contact")
                    .dueDate(LocalDateTime.now())
                    .startingDate(LocalDateTime.of(LocalDate.of(2022, 10, 5), LocalTime.now()))
                    .period("3")
                    .onOffline("online")
                    .status(Board.Status.OPENING)
                    .type(Board.Type.PROJECT)
                    .user(user1)
                    .build();

    @Override
    public Board create(Board post) {
        return board1;
    }

    @Override
    public Board getBoard(long boardId) {
        return board1;
    }

    @Override
    public Board update(Board patch) {
        return patch;
    }

    @Override
    public void delete(long boardId, long userId) {

    }
}
