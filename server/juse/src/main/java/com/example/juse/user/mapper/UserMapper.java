package com.example.juse.user.mapper;

import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.mapper.BoardMapper;
import com.example.juse.tag.mapper.TagMapper;
import com.example.juse.user.dto.UserRequestDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
uses = {BoardMapper.class, TagMapper.class})
public interface UserMapper {

    @Mapping(target = "skillStackTags", source = "skillStackTags")
    UserResponseDto userResponseDto(User user);

    @Mapping(target = "myBoards", source = "boardList")
    UserResponseDto.MyJuse toMyJuseDtoFrom(User entity);

    @Mapping(target = "skillStackTags", source = "skillStackTags")
    UserResponseDto.MyProfile toMyProfileDtoFrom(User entity);

    @Mapping(target = "skillStackTags", source = "skillStackTags")
    UserResponseDto.Profile toProfileDtoFrom(User entity);

    @Mapping(target = "userTagList", source = "skillStackTags")
    User toEntityFrom(UserRequestDto.Patch patch);

    @Mapping(target = "userTagList", source = "skillStackTags")
    User toEntityFrom(UserRequestDto.Post post);

    @BeanMapping(
            ignoreByDefault = true,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "img", source = "img")
    @Mapping(target = "introduction", source = "introduction")
    @Mapping(target = "portfolio", source = "portfolio")
    @Mapping(target = "nickname", source = "nickname")
    void updateEntityFromSource(@MappingTarget User entity, User source);

    default void updateDtoFromEntity(@MappingTarget List<BoardResponseDto.Multi> boardList, User entity) {
        boardList.forEach(
                board -> {
                    if (entity.getBookmarkList().stream().anyMatch(
                            bookmark -> bookmark.getBoard().getId() == board.getId()
                    )) {
                        board.setBookmarked(true);
                    }
                }
        );
    }
}
