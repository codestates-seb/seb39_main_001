package com.example.juse.user.dto;

import com.example.juse.board.dto.BoardResponseDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String introduction;
    private String email;
    private String portfolio;
    private String nickname;
    @Setter
    private String img;
    private String myImg;

    private List<String> skillStackTags;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyJuse {

        private Long id;
        private List<BoardResponseDto.Multi> myBookmarkList;
        private List<BoardResponseDto.Multi> myParticipationList;
        private List<BoardResponseDto.Multi> myBoards;
        private List<BoardResponseDto.Multi> myApplicationList;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyProfile {

        private long id;
        private String introduction;
        private String email;
        private String portfolio;
        private String nickname;
        private List<String> skillStackTags;
        private List<UserResponseDto> myUserList;
        private int liked;
        @Setter
        private String img;
        private String myImg;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Profile {

        private long id;
        private String introduction;
        private String email;
        private String portfolio;
        private String nickname;
        private List<String> skillStackTags;
        private int liked;
        @Setter
        private String img;
        private String myImg;

        @Setter
        private boolean isLikedByMe;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Brief {

        private long id;
        private String nickname;
        private String img;
    }


}