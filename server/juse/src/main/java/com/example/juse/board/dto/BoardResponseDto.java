package com.example.juse.board.dto;

import com.example.juse.application.dto.ApplicationResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.question.dto.QuestionResponseDto;
import com.example.juse.user.dto.UserResponseDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BoardResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Single {

        @Setter
        private boolean isAuth;
        @Setter
        private boolean isBookmarked;

        @Setter
        private boolean isWriterLiked;

        private Long id;
        private String title;

        private int backend;
        private int curBackend;
        private int frontend;
        private int curFrontend;
        private int designer;
        private int curDesigner;
        private int etc;
        private int curEtc;
        private int people;
        private String contact;
        private LocalDate dueDate;
        private LocalDate startingDate;
        private String period;
        private String onOffline;
        private String content;
        private int views;
        private int bookmarks;

        private List<String> tagList;
        private List<ApplicationResponseDto> applicationList;
        private List<QuestionResponseDto> questionList;

        private Board.Type type;
        private Board.Status status;

        @Setter
        private UserResponseDto user;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Multi {

        private long id;

        @Setter
        private boolean isBookmarked;

        @Setter
        private boolean isWriterLiked;

        private Board.Type type;
        private Board.Status status;
        private LocalDate startingDate;
        private String title;
        private String period;
        private List<String> tagList;

        private int views;
        private int bookmarks;
        private UserResponseDto user;

        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }

}
