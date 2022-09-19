package com.example.juse.application.controller;

import com.example.juse.application.dto.ApplicationRequestDto;
import com.example.juse.application.dto.ApplicationResponseDto;
import com.example.juse.application.entity.Application;
import com.example.juse.application.mapper.ApplicationMapper;
import com.example.juse.application.service.ApplicationService;
import com.example.juse.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/applications")
@RestController
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    @PostMapping("/{board-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<ApplicationResponseDto>> post(
            @PathVariable("board-id") long boardId,
            @PathVariable("user-id") long userId,
            @RequestParam(required = true, name = "position") String position
    ) {
        ApplicationRequestDto.Post post =
                ApplicationRequestDto.Post.builder()
                        .boardId(boardId)
                        .userId(userId)
                        .position(position)
                        .build();

        Application mappedObj = applicationMapper.toEntityFrom(post);
        Application createdEntity = applicationService.create(mappedObj);
        ApplicationResponseDto responseDto = applicationMapper.toResponseDtoFrom(createdEntity);
        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }


    @PatchMapping("/{application-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<ApplicationResponseDto>> accept(
            @PathVariable("application-id") long applicationId,
            @PathVariable("user-id") long userId
    ) {
        Application acceptedEntity = applicationService.accept(applicationId, userId);
        ApplicationResponseDto responseDto = applicationMapper.toResponseDtoFrom(acceptedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{application-id}/{user-id}")
    public void deny(
            @PathVariable("application-id") long applicationId,
            @PathVariable("user-id") long userId
    ) {
        applicationService.deny(applicationId, userId);
    }
}
