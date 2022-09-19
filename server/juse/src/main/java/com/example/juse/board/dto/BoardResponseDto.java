package com.example.juse.board.dto;

import com.example.juse.application.dto.ApplicationResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.question.dto.QuestionResponseDto;
import com.example.juse.user.dto.UserResponseDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Single {

        private Long id;
        private String title;

        private int backend;
        private int frontend;
        private int designer;
        private int etc;
        private int people;
        private String contact;
        private LocalDate dueDate;
        private LocalDate startingDate;
        private String period;
        private String onOffline;
        private String content;
        private int views;
        private int bookmarks;
        private int liked;

        private List<String> tagList;
        private List<ApplicationResponseDto> applicationList;
        private List<QuestionResponseDto> questionList;

        private Board.Type type;
        private Board.Status status;

        private UserResponseDto user;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Multi {

        private long id;
        private Board.Type type;
        private Board.Status status;
        private LocalDate startingDate;
        private String title;
        private List<String> tagList;

        private int views;
        private int bookmarks;
        private UserResponseDto user;

    }

}
