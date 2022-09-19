package com.example.juse.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseDto {
    private long id;
    private Byte[] profileImage;
    private String introduction;
    private String email;
    private String portfolio;
    private String nickname;
}
