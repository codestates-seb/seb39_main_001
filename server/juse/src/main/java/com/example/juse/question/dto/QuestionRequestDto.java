package com.example.juse.question.dto;

import lombok.*;

public class QuestionRequestDto {


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        private String content;

        @Setter
        private Long boardId;

        @Setter
        private Long userId;

    }

}
