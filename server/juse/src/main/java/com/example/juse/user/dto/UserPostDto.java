package com.example.juse.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class UserPostDto {
    private Byte[] profileImage;
    private String nickname;
    private String portfolio;
    private String introduction;

    private List<String> skillStackTags;
}
