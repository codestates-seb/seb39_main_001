package com.example.juse.bookmark.service;

import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"test"})
@RequiredArgsConstructor
@Service
public class BookmarkServiceStub implements BookmarkService {

    private final StubService stubService;

    @Override
    public Bookmark create(long boardId, long userId) {
        return stubService.getBookmark();
    }

    @Override
    public void delete(long boardId, long userId) {

    }
}
