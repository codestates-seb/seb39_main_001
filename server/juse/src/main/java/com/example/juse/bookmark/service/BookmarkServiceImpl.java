package com.example.juse.bookmark.service;

import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService{

    private final BookmarkRepository bookmarkRepository;

    @Override
    public Bookmark create(long boardId, long userId) {
        return null;
    }

    @Override
    public void delete(long boardId, long userId) {

    }
}
