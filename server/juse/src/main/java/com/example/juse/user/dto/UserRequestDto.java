package com.example.juse.user.dto;

import lombok.*;

import java.util.List;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Patch {

        @Setter
        private Long id;
        private String introduction;
        private String portfolio;
        private String nickname;

        private List<String> skillStackTags;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        private String introduction;
        private String portfolio;
        private String nickname;

        private List<String> skillStackTags;

    }

}
