package com.example.juse.answer.dto;

import lombok.*;

public class AnswerRequestDto {


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        private String content;

        @Setter
        private Long questionId;

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
        private Long answerId;

        @Setter
        private Long userId;

    }

}