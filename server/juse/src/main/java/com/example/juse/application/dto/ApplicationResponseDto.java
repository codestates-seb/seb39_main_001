package com.example.juse.application.dto;

import com.example.juse.application.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDto {

    private Long id;
    private Long userId;
    private Long boardId;
    private String position;
    private Application.Status status;
    private String nickname;

}
