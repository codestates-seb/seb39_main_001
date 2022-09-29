package com.example.juse.application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ApplicationRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        private Long boardId;
        private Long userId;
        private String position;
        private String nickname;

    }

}
