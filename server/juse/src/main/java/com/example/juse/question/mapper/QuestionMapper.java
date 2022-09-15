package com.example.juse.question.mapper;

import com.example.juse.question.dto.QuestionRequestDto;
import com.example.juse.question.dto.QuestionResponseDto;
import com.example.juse.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "board.id", source = "boardId")
    Question toEntityFrom(QuestionRequestDto.Post postDto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "boardId", source = "board.id")
    QuestionResponseDto toResponseDtoFrom(Question entity);
}
