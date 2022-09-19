package com.example.juse.answer.mapper;

import com.example.juse.answer.dto.AnswerRequestDto;
import com.example.juse.answer.dto.AnswerResponseDto;
import com.example.juse.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    @Mapping(target = "question.id", source = "questionId")
    @Mapping(target = "user.id", source = "userId")
    Answer toEntityFrom(AnswerRequestDto.Post postDto);

    @Mapping(target = "id", source = "answerId")
    @Mapping(target = "user.id", source = "userId")
    Answer toEntityFrom(AnswerRequestDto.Patch patchDto);

    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "userId", source = "user.id")
    AnswerResponseDto toResponseDtoFrom(Answer entity);
}
