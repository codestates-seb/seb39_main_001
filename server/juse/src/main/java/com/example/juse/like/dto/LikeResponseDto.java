package com.example.juse.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponseDto {

    private long id;
    private long whoLikes;
    private long whoIsLiked;

}