package com.example.juse.bookmark.mapper;

import com.example.juse.bookmark.dto.BookmarkResponseDto;
import com.example.juse.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    @Mapping(target = "boardId", source = "board.id")
    @Mapping(target = "userId", source = "user.id")
    BookmarkResponseDto toResponseDtoFrom(Bookmark entity);
}
