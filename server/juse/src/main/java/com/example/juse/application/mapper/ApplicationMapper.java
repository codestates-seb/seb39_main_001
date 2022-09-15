package com.example.juse.application.mapper;

import com.example.juse.application.dto.ApplicationRequestDto;
import com.example.juse.application.dto.ApplicationResponseDto;
import com.example.juse.application.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "board.id", source = "boardId")
    Application toEntityFrom(ApplicationRequestDto.Post post);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "boardId", source = "board.id")
    ApplicationResponseDto toResponseDtoFrom(Application entity);

}
