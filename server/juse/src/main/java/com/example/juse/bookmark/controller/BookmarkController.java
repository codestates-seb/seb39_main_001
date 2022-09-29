package com.example.juse.bookmark.controller;

import com.example.juse.bookmark.dto.BookmarkResponseDto;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.bookmark.mapper.BookmarkMapper;
import com.example.juse.bookmark.service.BookmarkService;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.exception.validator.NotEmptyToken;
import com.example.juse.security.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Validated
@RequestMapping("/bookmarks")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkMapper bookmarkMapper;

    @PostMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<BookmarkResponseDto>> post(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("board-id") @Positive long boardId
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        Bookmark createdEntity = bookmarkService.create(boardId, userId);
        BookmarkResponseDto responseDto = bookmarkMapper.toResponseDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{board-id}")
    public void delete(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("board-id") @Positive long boardId

    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        bookmarkService.delete(boardId, userId);
    }
}
