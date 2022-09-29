package com.example.juse.answer.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AnswerRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        @NotNull
        private String content;

        @Setter
        @Positive
        private Long questionId;

        @Setter
        @Positive
        private Long userId;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Patch {

        private String content;

        @Setter
        @Positive
        private Long answerId;

        @Setter
        @Positive
        private Long userId;

    }

}