package com.example.juse.board.dto;

import com.example.juse.board.entity.Board;
import com.example.juse.user.dto.UserResponseDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BoardResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Single {

        private Long id;

        private Integer backend;
        private Integer frontend;
        private Integer designer;
        private Integer etc;
        private Integer people;
        private String contact;
        private LocalDate dueDate;
        private LocalDate startingDate;
        private String period;
        private String onOffline;
        private String content;

        private Board.Type type;
        private Board.Status status;

        private UserResponseDto user;
    }

}
