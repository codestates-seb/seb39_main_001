package com.example.juse.board.mapper;

import com.example.juse.application.mapper.ApplicationMapper;
import com.example.juse.board.dto.BoardRequestDto;
import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.question.mapper.QuestionMapper;
import com.example.juse.tag.mapper.TagMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
uses = {ApplicationMapper.class, QuestionMapper.class, TagMapper.class})
public interface BoardMapper {

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "boardTagList", source = "tagList")
    Board toEntityFrom(BoardRequestDto.Post postDto);

    @Mapping(target = "tagList", source = "tagNames")
    BoardResponseDto.Single toSingleResponseDto(Board entity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "id", source = "boardId")
    @Mapping(target = "boardTagList", source = "tagList")
    Board toEntityFrom(BoardRequestDto.Patch patchDto);

    @Mapping(target = "tagList", source = "tagNames")
    BoardResponseDto.Multi toMultiResponseDto(Board entity);

    List<BoardResponseDto.Multi> toListDtoFromListEntities(List<Board> entities);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            ignoreByDefault = true
    )
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "contact", source = "contact")
    @Mapping(target = "dueDate", source = "dueDate")
    @Mapping(target = "startingDate", source = "startingDate")
    @Mapping(target = "period", source = "period")
    @Mapping(target = "onOffline", source = "onOffline")
    @Mapping(target = "status", source = "status")
    void updateEntityFromSource(@MappingTarget Board entity, Board source);
}
