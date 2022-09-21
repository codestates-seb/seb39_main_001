package com.example.juse.question.dto;

import com.example.juse.answer.dto.AnswerResponseDto;
import com.example.juse.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDto {

    private long id;
    private String content;
    private UserResponseDto.Brief user;
    private AnswerResponseDto.AnswerToQuestion answer;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}