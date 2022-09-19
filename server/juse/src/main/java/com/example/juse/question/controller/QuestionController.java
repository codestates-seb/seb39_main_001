package com.example.juse.question.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.question.dto.QuestionRequestDto;
import com.example.juse.question.dto.QuestionResponseDto;
import com.example.juse.question.entity.Question;
import com.example.juse.question.mapper.QuestionMapper;
import com.example.juse.question.service.QuestionService;
import com.example.juse.security.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PatchMapping("/{board-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<QuestionResponseDto>> update(
            @PathVariable("board-id") long boardId,
            @PathVariable("user-id") long userId,
            @RequestBody QuestionRequestDto.Patch patchDto
    ) {
        patchDto.setUserId(userId);
        Question mappedObj = questionMapper.toEntityFrom(patchDto);
        Question updatedEntity = questionService.update(mappedObj);
        QuestionResponseDto responseDto = questionMapper.toResponseDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{question-id}")
    public void delete(
            @PathVariable("question-id") long questionId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
            ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        questionService.delete(questionId, userId);
    }
}
