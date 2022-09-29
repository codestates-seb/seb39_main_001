package com.example.juse.application.controller;

import com.example.juse.application.dto.ApplicationRequestDto;
import com.example.juse.application.dto.ApplicationResponseDto;
import com.example.juse.application.entity.Application;
import com.example.juse.application.mapper.ApplicationMapper;
import com.example.juse.application.service.ApplicationService;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.exception.validator.NotEmptyToken;
import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RequestMapping("/applications")
@RestController
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;
    private final UserService userService;

    @PostMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<ApplicationResponseDto>> post(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("board-id") @Positive long boardId,
            @RequestParam(required = true, name = "position") @NotBlank String position
    ) {
        Long userId = principalDetails.getSocialUser().getUser().getId();
        String nickname = principalDetails.getSocialUser().getUser().getNickname();

        ApplicationRequestDto.Post post =
                ApplicationRequestDto.Post.builder()
                        .boardId(boardId)
                        .userId(userId)
                        .nickname(nickname)
                        .position(position)
                        .build();

        Application mappedObj = applicationMapper.toEntityFrom(post);
        Application createdEntity = applicationService.create(mappedObj, boardId);
        ApplicationResponseDto responseDto = applicationMapper.toResponseDtoFrom(createdEntity);
        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }


    @PatchMapping("/{application-id}")
    public ResponseEntity<SingleResponseDto<ApplicationResponseDto>> accept(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("application-id") @Positive long applicationId
    ) {
        Long userId = principalDetails.getSocialUser().getUser().getId();
        Application acceptedEntity = applicationService.accept(applicationId, userId);
        ApplicationResponseDto responseDto = applicationMapper.toResponseDtoFrom(acceptedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{application-id}")
    public void deny(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("application-id") @Positive long applicationId
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        applicationService.deny(applicationId, userId);
    }
}
