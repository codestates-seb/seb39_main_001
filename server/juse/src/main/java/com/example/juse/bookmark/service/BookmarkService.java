package com.example.juse.bookmark.service;

import com.example.juse.bookmark.entity.Bookmark;

public interface BookmarkService {

    Bookmark create(long boardId, long userId);

    void delete(long boardId, long userId);
}
