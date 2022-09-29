package com.example.juse.board.dto;

import com.example.juse.board.entity.Board;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BoardRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
        private Integer backend;
        private Integer frontend;
        private Integer designer;
        private Integer etc;
        private Integer people;
        private String contact;
        private LocalDate dueDate;
        private LocalDate startingDate;

        @NotBlank
        private String period;
        private String onOffline;

        @NotNull
        private Board.Type type;

        @NotEmpty
        @Builder.Default
        private List<String> tagList = new ArrayList<>();

        @Setter
        @Positive
        private Long userId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Patch {

        private String title;
        private String content;
        private String contact;
        private LocalDate dueDate;
        private LocalDate startingDate;

        @NotBlank
        private String period;
        private String onOffline;

        @NotEmpty
        @Builder.Default
        private List<String> tagList = new ArrayList<>();

        @NotNull
        private Board.Type type;

        @Setter
        @Positive
        private Long userId;

        @Setter
        @Positive
        private Long boardId;
    }

}