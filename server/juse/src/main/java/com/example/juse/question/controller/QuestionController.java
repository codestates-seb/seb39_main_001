package com.example.juse.question.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.question.dto.QuestionRequestDto;
import com.example.juse.question.dto.QuestionResponseDto;
import com.example.juse.question.entity.Question;
import com.example.juse.question.mapper.QuestionMapper;
import com.example.juse.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/questions")
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @PostMapping("/{board-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<QuestionResponseDto>> post(
            @PathVariable("board-id") long boardId,
            @PathVariable("user-id") long userId,
            @RequestBody QuestionRequestDto.Post postDto
    ) {
        postDto.setUserId(userId);
        postDto.setBoardId(boardId);
        Question mappedObj = questionMapper.toEntityFrom(postDto);
        Question createdEntity = questionService.create(mappedObj);
        QuestionResponseDto responseDto = questionMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

}
