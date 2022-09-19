package com.example.juse.user.dto;

import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.bookmark.dto.BookmarkResponseDto;
import com.example.juse.tag.entity.UserTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String introduction;
    private String email;
    private String portfolio;
    private String nickname;

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
    public static class Profile {

        private long id;
        private String introduction;
        private String email;
        private String portfolio;
        private String nickname;

        private List<String> skillStackTags;
        private List<UserResponseDto> myUserList;
        private int liked;

    }

}