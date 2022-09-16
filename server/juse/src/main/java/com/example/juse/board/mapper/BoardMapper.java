package com.example.juse.board.mapper;

import com.example.juse.application.mapper.ApplicationMapper;
import com.example.juse.board.dto.BoardRequestDto;
import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.question.mapper.QuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
uses = {ApplicationMapper.class, QuestionMapper.class})
public interface BoardMapper {

    Board toEntityFrom(BoardRequestDto.Post postDto);

    @Mapping(target = "tagList", source = "tagNames")
    BoardResponseDto.Single toSingleResponseDto(Board entity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "id", source = "boardId")
    Board toEntityFrom(BoardRequestDto.Patch patchDto);

    @Mapping(target = "tagList", source = "tagNames")
    BoardResponseDto.Multi toMultiResponseDto(Board entity);

    List<BoardResponseDto.Multi> toListDtoFromListEntities(List<Board> entities);
}
