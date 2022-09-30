package com.example.juse.question.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.exception.validator.NotEmptyToken;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RequestMapping("/questions")
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @PostMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<QuestionResponseDto>> post(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("board-id") @Positive long boardId,
            @RequestBody @Valid QuestionRequestDto.Post postDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        postDto.setUserId(userId);
        postDto.setBoardId(boardId);
        Question mappedObj = questionMapper.toEntityFrom(postDto);
        Question createdEntity = questionService.create(mappedObj);
        QuestionResponseDto responseDto = questionMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity<SingleResponseDto<QuestionResponseDto>> update(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("question-id") @Positive long questionId,
            @RequestBody QuestionRequestDto.Patch patchDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        patchDto.setUserId(userId);
        patchDto.setQuestionId(questionId);
        Question mappedObj = questionMapper.toEntityFrom(patchDto);
        Question updatedEntity = questionService.update(mappedObj);
        QuestionResponseDto responseDto = questionMapper.toResponseDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{question-id}")
    public void delete(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("question-id") @Positive long questionId
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        questionService.delete(questionId, userId);
    }
}
