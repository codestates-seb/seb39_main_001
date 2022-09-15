package com.example.juse.board.controller;

import com.example.juse.board.dto.BoardRequestDto;
import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.board.mapper.BoardMapper;
import com.example.juse.board.service.BoardSerivice;
import com.example.juse.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardSerivice boardSerivice;
    private final BoardMapper boardMapper;

    @PostMapping("/{user-id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> post(
            @PathVariable("user-id") long userId,
            @RequestBody BoardRequestDto.Post postDto
    ) {
        postDto.setUserId(userId);
        Board mappedObj = boardMapper.toEntityFrom(postDto);
        Board createdEntity = boardSerivice.create(mappedObj);
        BoardResponseDto.Single responseDto = boardMapper.toDto(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> getBoard(
            @PathVariable("board-id") long boardId
    ) {
        Board foundEntity = boardSerivice.getBoard(boardId);
        BoardResponseDto.Single responseDto = boardMapper.toDto(foundEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }


    @PatchMapping("/{board-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> patch(
            @PathVariable("board-id") long boardId,
            @PathVariable("user-id") long userId,
            @RequestBody BoardRequestDto.Patch patchDto
    ) {

        patchDto.setUserId(userId);
        patchDto.setBoardId(boardId);
        Board mappedObj = boardMapper.toEntityFrom(patchDto);
        Board updatedEntity = boardSerivice.update(mappedObj);
        BoardResponseDto.Single responseDto = boardMapper.toDto(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<String>> delete(
            @PathVariable("board-id") long boardId,
            @PathVariable("user-id") long userId
    ) {

        boardSerivice.delete(boardId, userId);
        return new ResponseEntity<>(new SingleResponseDto<>("success"), HttpStatus.NO_CONTENT);

    }
}
