package com.example.juse.answer.dto;

import com.example.juse.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponseDto {

    private long id;
    private String content;

    private long questionId;
    private long userId;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerToQuestion{

        private long id;

        private UserResponseDto.Brief user;
        private String content;
    }
}