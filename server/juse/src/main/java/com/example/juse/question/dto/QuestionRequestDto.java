package com.example.juse.question.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

public class QuestionRequestDto {


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        @NotNull
        private String content;

        @Setter
        private Long boardId;

        @Setter
        private Long userId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Patch {

        private String content;

        @Setter
        private Long questionId;

        @Setter
        private Long userId;
    }


}
