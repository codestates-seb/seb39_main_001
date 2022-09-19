package com.example.juse.answer.controller;

import com.example.juse.answer.dto.AnswerRequestDto;
import com.example.juse.answer.dto.AnswerResponseDto;
import com.example.juse.answer.entity.Answer;
import com.example.juse.answer.mapper.AnswerMapper;
import com.example.juse.answer.service.AnswerService;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.security.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/answers")
@RestController
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    @PostMapping("/{question-id}")
    public ResponseEntity<SingleResponseDto<AnswerResponseDto>> post(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("question-id") long questionId,
            @RequestBody AnswerRequestDto.Post postDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        postDto.setUserId(userId);
        postDto.setUserId(questionId);
        Answer mappedObj = answerMapper.toEntityFrom(postDto);
        Answer createdEntity = answerService.create(mappedObj);
        AnswerResponseDto responseDto = answerMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity<SingleResponseDto<AnswerResponseDto>> update(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("answer-id") long answerId,
            @RequestBody AnswerRequestDto.Patch patchDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        patchDto.setAnswerId(answerId);
        patchDto.setUserId(userId);

        Answer mappedObj = answerMapper.toEntityFrom(patchDto);
        Answer updatedEntity = answerService.update(mappedObj);
        AnswerResponseDto responseDto = answerMapper.toResponseDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{answer-id}")
    public void delete(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("answer-id") long answerId
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        answerService.delete(answerId, userId);
    }
}
