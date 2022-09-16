package com.example.juse.helper.stubservice;

import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.question.entity.Question;
import com.example.juse.tag.entity.BoardTag;
import com.example.juse.tag.entity.Tag;
import com.example.juse.user.entity.User;
import org.hibernate.validator.internal.metadata.aggregated.FieldCascadable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Profile("test")
@Component
public class StubService {

    @Bean
    public User getUser() {
        return  User.builder()
                .id(1L)
                .introduction("user1")
                .email("user1")
                .portfolio("github.com/user1")
                .nickname("user mouth")
                .build();
    }


    @Bean
    public Board getBoard() {
        Board stub = Board.builder().id(1L).build();
        return Board.builder()
                .id(1L)
                .title("stub1")
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
                .user(getUser())
                .bookmarks(4)
                .liked(4)
                .views(4)
                .boardTagList(
                        List.of(
                                BoardTag.builder()
                                        .id(1L)
                                        .tag(getTag1())
                                        .board(stub)
                                        .build(),
                                BoardTag.builder()
                                        .id(2L)
                                        .tag(getTag2())
                                        .board(stub)
                                        .build()
                        ))
                .applicationList(
                        List.of(
                                Application.builder()
                                        .id(1L)
                                        .board(stub)
                                        .user(User.builder()
                                                .id(1L).build())
                                        .isAccepted(false)
                                        .position("backend")
                                        .build(),
                                Application.builder()
                                        .id(2L)
                                        .board(stub)
                                        .user(User.builder()
                                                .id(2L).build())
                                        .isAccepted(true)
                                        .position("backend")
                                        .build()
                        )
                )

                .questionList(
                        List.of(
                                Question.builder()
                                        .id(1L)
                                        .board(stub)
                                        .user(getUser())
                                        .content("question1")
                                        .build()
                                ))
                .build();
    }

    @Bean
    public Application getApplication() {
        return Application.builder()
                .id(1L)
                .user(getUser())
                .board(getBoard())
                .position("backend")
                .build();
    }

    @Bean
    public Application getAcceptedApplication() {
        return Application.builder()
                .id(1L)
                .user(getUser())
                .board(getBoard())
                .position("backend")
                .isAccepted(true)
                .build();
    }

    @Bean
    public Bookmark getBookmark() {
        return Bookmark.builder()
                .id(1L)
                .user(getUser())
                .board(getBoard())
                .build();
    }

    @Bean
    public Question getQuestion() {
        return Question.builder()
                .id(1L)
                .content("정신이 있나요?")
                .user(getUser())
                .board(getBoard())
                .build();
    }

    @Bean
    public Tag getTag1() {
        return Tag.builder()
                .id(1L)
                .type(Tag.Type.BACKEND)
                .name("java")
                .build();
    }

    @Bean
    public Tag getTag2() {
        return Tag.builder()
                .id(2L)
                .type(Tag.Type.FRONTEND)
                .name("react")
                .build();
    }

    @Bean
    public List<BoardTag> getBoardTagList() {
        return List.of(
                BoardTag.builder()
                        .id(1L)
                        .tag(getTag1())
                        .build(),
                BoardTag.builder()
                        .id(2L)
                        .tag(getTag2())
                        .build()
        );
    }


}
