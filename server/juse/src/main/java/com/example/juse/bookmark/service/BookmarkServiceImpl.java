package com.example.juse.bookmark.service;

import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("real")
@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Override
    public Bookmark create(long boardId, long userId) {
        return null;
    }

    @Override
    public void delete(long boardId, long userId) {

    }
}
