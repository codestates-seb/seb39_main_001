package com.example.juse.helper.stubservice;

import com.example.juse.answer.entity.Answer;
import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.like.entity.Like;
import com.example.juse.question.entity.Question;
import com.example.juse.tag.entity.BoardTag;
import com.example.juse.tag.entity.Tag;
import com.example.juse.tag.entity.UserTag;
import com.example.juse.user.entity.User;
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
                .boardList(
                        List.of(
                                Board.builder()
                                        .id(23L)
                                        .type(Board.Type.STUDY)
                                        .people(5)
                                        .title("흐느껴 울고 웃으며어~")
                                        .dueDate(LocalDate.of(2099, 10, 10))
                                        .onOffline("이곳저곳")
                                        .build()
                        )
                )
                .userTagList(
                        List.of(
                                UserTag.builder()
                                        .id(1L)
                                        .tag(
                                                Tag.builder()
                                                        .name("java 11")
                                                        .build()
                                        ).build(),
                                UserTag.builder()
                                        .id(2L)
                                        .tag(
                                                Tag.builder()
                                                        .name("react")
                                                        .build()
                                        ).build(),
                                UserTag.builder()
                                        .id(5L)
                                        .tag(
                                                Tag.builder()
                                                        .name("laziness")
                                                        .build()
                                        ).build()
                        )
                )
                .liked(10)
                .likeList(
                        List.of(
                                Like.builder()
                                        .user(
                                                User.builder()
                                                        .id(10L)
                                                        .nickname("젠틀맨곽철용")
                                                        .build()
                                        ).build(),
                                Like.builder()
                                        .user(
                                                User.builder()
                                                        .id(999L)
                                                        .nickname("달건이곽철용")
                                                        .build()
                                        ).build()
                        )
                )
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

    @Bean
    public Answer getAnswer() {
        return Answer.builder()
                .id(1L)
                .content("중구가시키드나")
                .question(getQuestion())
                .user(User.builder().id(1L).build())
                .build();
    }

    @Bean
    public User getMyJuse() {
        return User.builder()
                .id(1L)
                .boardList(
                        List.of(
                                getBoard()
                        )
                )
                .bookmarkList(
                        List.of(
                                Bookmark.builder()
                                        .id(1L)
                                        .board(getBoard())
                                        .build(),
                                Bookmark.builder()
                                        .id(2L)
                                        .board(
                                                Board.builder()
                                                        .id(2L)
                                                        .title("board2")
                                                        .dueDate(LocalDate.of(2022, 10, 15))
                                                        .period("6")
                                                        .user(
                                                                User.builder()
                                                                        .id(1L)
                                                                        .nickname("user2")
                                                                        .build()
                                                        )
                                                        .type(Board.Type.STUDY)
                                                        .views(100)
                                                        .bookmarks(2)
                                                        .build()
                                        ).build()
                        )
                ).applicationList(
                        List.of(
                                Application.builder()
                                        .board(
                                                Board.builder()
                                                        .id(3L)
                                                        .title("aws project")
                                                        .bookmarks(5)
                                                        .views(15)
                                                        .type(Board.Type.PROJECT)
                                                        .dueDate(LocalDate.of(2022, 10, 19))
                                                        .period("6")
                                                        .user(
                                                                User.builder()
                                                                        .id(5L)
                                                                        .nickname("민수씨!")
                                                                        .build()
                                                        ).build()
                                        )
                                        .isAccepted(true)
                                        .build(),
                                Application.builder()
                                        .board(
                                                Board.builder()
                                                        .id(4L)
                                                        .title("spitz")
                                                        .bookmarks(10)
                                                        .views(24)
                                                        .type(Board.Type.STUDY)
                                                        .dueDate(LocalDate.of(2022, 11, 25))
                                                        .period("long")
                                                        .user(
                                                                User.builder()
                                                                        .id(5L)
                                                                        .nickname("용남이형!")
                                                                        .build()
                                                        ).build()
                                        )
                                        .isAccepted(false)
                                        .build()
                        )
                )
                .build();

    }

}
