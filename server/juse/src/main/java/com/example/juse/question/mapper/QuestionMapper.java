package com.example.juse.question.mapper;

import com.example.juse.question.dto.QuestionRequestDto;
import com.example.juse.question.dto.QuestionResponseDto;
import com.example.juse.question.entity.Question;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "board.id", source = "boardId")
    Question toEntityFrom(QuestionRequestDto.Post postDto);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "id", source = "questionId")
    Question toEntityFrom(QuestionRequestDto.Patch patchDto);


    QuestionResponseDto toResponseDtoFrom(Question entity);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            ignoreByDefault = true
    )
    @Mapping(target = "content", source = "content")
    void updateEntityFromSource(@MappingTarget Question entity, Question source);
}
