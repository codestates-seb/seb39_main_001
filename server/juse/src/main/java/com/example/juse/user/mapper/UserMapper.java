package com.example.juse.user.mapper;

import com.example.juse.user.dto.UserPostDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto userResponseDto(User user);

//    @Mapping(target = "userTagList", source = "tags")
//    User userFromDto(UserPostDto userPostDto);
}
