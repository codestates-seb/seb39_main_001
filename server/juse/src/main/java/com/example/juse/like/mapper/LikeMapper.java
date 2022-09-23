package com.example.juse.like.mapper;

import com.example.juse.like.dto.LikeResponseDto;
import com.example.juse.like.entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(target = "whoLikes", source = "whoLikes.id")
    @Mapping(target = "whoIsLiked", source = "whoIsLiked.id")
    LikeResponseDto toResponseDtoFrom(Like entity);
}
