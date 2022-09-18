package com.example.juse.board.dto;

import com.example.juse.board.entity.Board;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BoardRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        private String content;
        private Integer backend;
        private Integer frontend;
        private Integer designer;
        private Integer etc;
        private Integer people;
        private String contact;
        private LocalDate dueDate;
        private LocalDate startingDate;
        private String period;
        private String onOffline;

        private Board.Type type;

        @Setter
        private Long userId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Patch {

        private String content;
        private String contact;
        private LocalDate dueDate;
        private LocalDate startingDate;
        private String period;
        private String onOffline;
        private List<String> tagList;

        private Board.Type type;

        @Setter
        private Long userId;

        @Setter
        private Long boardId;
    }

}