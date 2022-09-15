package com.example.juse.board.mapper;

import com.example.juse.board.dto.BoardRequestDto;
import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    Board toEntityFrom(BoardRequestDto.Post postDto);

    BoardResponseDto.Single toDto(Board entity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "id", source = "boardId")
    Board toEntityFrom(BoardRequestDto.Patch patchDto);
}
