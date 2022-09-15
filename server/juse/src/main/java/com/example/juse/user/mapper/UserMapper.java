package com.example.juse.user.mapper;

import com.example.juse.board.mapper.BoardMapper;
import com.example.juse.tag.mapper.TagMapper;
import com.example.juse.user.dto.UserRequestDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {BoardMapper.class, TagMapper.class})
public interface UserMapper {

    UserResponseDto.MyJuse toMyJuseDtoFrom(User entity);

    UserResponseDto toDtoFrom(User entity);

    UserResponseDto.Profile toProfileDtoFrom(User entity);

    @Mapping(target = "userTagList", source = "skillStackTags")
    User toEntityFrom(UserRequestDto.Patch patch);

    @Mapping(target = "userTagList", source = "skillStackTags")
    User toEntityFrom(UserRequestDto.Post post);

}
