package com.example.juse.like.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.like.dto.LikeResponseDto;
import com.example.juse.like.entity.Like;
import com.example.juse.like.mapper.LikeMapper;
import com.example.juse.like.service.LikeService;
import com.example.juse.security.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    @PostMapping("/{user-who-is-liked}")
    public ResponseEntity<SingleResponseDto<LikeResponseDto>> like(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("user-who-is-liked") long whoIsLiked
    ) {
        long whoLikes = principalDetails.getSocialUser().getUser().getId();
        Like createdEntity = likeService.create(whoLikes, whoIsLiked);
        LikeResponseDto responseDto = likeMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{user-who-is-liked}")
    public void delete(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("user-who-is-liked") long whoIsLiked
    ) {
        long whoLikes = principalDetails.getSocialUser().getUser().getId();

        likeService.delete(whoLikes, whoIsLiked);
    }


}
