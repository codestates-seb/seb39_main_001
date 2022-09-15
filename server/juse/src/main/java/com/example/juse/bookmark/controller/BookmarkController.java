package com.example.juse.bookmark.controller;

import com.example.juse.bookmark.dto.BookmarkResponseDto;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.bookmark.mapper.BookmarkMapper;
import com.example.juse.bookmark.service.BookmarkService;
import com.example.juse.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/bookmarks")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkMapper bookmarkMapper;

    @PostMapping("/{board-id}/{user-id}")
    public ResponseEntity<SingleResponseDto<BookmarkResponseDto>> post(
            @PathVariable("board-id") long boardId,
            @PathVariable("user-id") long userId
    ) {
        Bookmark createdEntity = bookmarkService.create(boardId, userId);
        BookmarkResponseDto responseDto = bookmarkMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{board-id}/{user-id}")
    public void delete(
            @PathVariable("board-id") long boardId,
            @PathVariable("user-id") long userId
    ) {
        bookmarkService.delete(boardId, userId);
    }
}
