package com.example.juse.like.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.like.dto.LikeResponseDto;
import com.example.juse.like.entity.Like;
import com.example.juse.like.mapper.LikeMapper;
import com.example.juse.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    @PostMapping("/{user-who-likes}/likes/{user-who-is-liked}")
    public ResponseEntity<SingleResponseDto<LikeResponseDto>> like(
        @PathVariable("user-who-likes") long whoLikes,
        @PathVariable("user-who-is-liked") long whoIsLiked
    ) {
        Like createdEntity = likeService.create(whoLikes, whoIsLiked);
        LikeResponseDto responseDto = likeMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{user-who-likes}/likes/{user-who-is-liked}")
    public void delete(
            @PathVariable("user-who-likes") long whoLikes,
            @PathVariable("user-who-is-liked") long whoIsLiked
    ) {
        likeService.delete(whoLikes, whoIsLiked);
    }


}
