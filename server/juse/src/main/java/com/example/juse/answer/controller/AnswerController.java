package com.example.juse.answer.controller;

import com.example.juse.answer.dto.AnswerRequestDto;
import com.example.juse.answer.dto.AnswerResponseDto;
import com.example.juse.answer.entity.Answer;
import com.example.juse.answer.mapper.AnswerMapper;
import com.example.juse.answer.service.AnswerService;
import com.example.juse.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/answers")
@RestController
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    @PostMapping("/{question-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<AnswerResponseDto>> post(
            @PathVariable("question-id") long questionId,
            @PathVariable("user-id") long userId,
            @RequestBody AnswerRequestDto.Post postDto
    ) {
        postDto.setUserId(userId);
        postDto.setUserId(questionId);
        Answer mappedObj = answerMapper.toEntityFrom(postDto);
        Answer createdEntity = answerService.create(mappedObj);
        AnswerResponseDto responseDto = answerMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{answer-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<AnswerResponseDto>> update(
            @PathVariable("answer-id") long answerId,
            @PathVariable("user-id") long userId,
            @RequestBody AnswerRequestDto.Patch patchDto
    ) {
        patchDto.setAnswerId(answerId);
        patchDto.setUserId(userId);

        Answer mappedObj = answerMapper.toEntityFrom(patchDto);
        Answer updatedEntity = answerService.update(mappedObj);
        AnswerResponseDto responseDto = answerMapper.toResponseDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{answer-id}/{user-id}")
    public void delete(
            @PathVariable("answer-id") long answerId,
            @PathVariable("user-id") long userId
    ) {
        answerService.delete(answerId, userId);
    }
}
