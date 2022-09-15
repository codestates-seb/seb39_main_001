package com.example.juse.helper.stubservice;

import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Profile("test")
@Component
public class StubService {

    @Bean
    public User getUserStub() {
        return  User.builder()
                .id(1L)
                .introduction("user1")
                .email("user1")
                .portfolio("github.com/user1")
                .nickname("user mouth")
                .build();
    }


    @Bean
    public Board getBoardStub() {
        return Board.builder()
                .id(1L)
                .content("content1")
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
                .user(getUserStub())
                .build();
    }

    @Bean
    public Application getApplicationStub() {
        return Application.builder()
                .id(1L)
                .user(getUserStub())
                .board(getBoardStub())
                .position("backend")
                .build();
    }


}
