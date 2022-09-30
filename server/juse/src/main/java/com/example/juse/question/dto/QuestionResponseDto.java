package com.example.juse.question.dto;

import com.example.juse.answer.dto.AnswerResponseDto;
import com.example.juse.user.dto.UserResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDto {

    private long id;
    private String content;
    private UserResponseDto.Brief user;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Setter
    private boolean isAuth;

    private AnswerResponseDto.AnswerToQuestion answer;
}