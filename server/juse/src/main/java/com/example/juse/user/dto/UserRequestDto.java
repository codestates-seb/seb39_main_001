package com.example.juse.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
        private String img;

        @Setter
        private String email;

        @Setter
        private Long socialUserId;

        @Builder.Default
        private List<String> skillStackTags = new ArrayList<>();

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Post {

        @Setter
        private long userId;

        @NotBlank
        private String introduction;
        private String portfolio;

        @NotBlank
        private String nickname;

        @Builder.Default
        private List<String> skillStackTags = new ArrayList<>();

        @Builder.Default
        private String img = "default.jpg";

    }

}
