package com.example.juse.application.mapper;

import com.example.juse.application.dto.ApplicationRequestDto;
import com.example.juse.application.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "board.id", source = "boardId")
    Application toEntityFrom(ApplicationRequestDto.Post post);
}
