package com.example.juse.bookmark.repository;

import com.example.juse.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // boardId 와 userId 를 통해 Bookmark 객체를 찾아온다.
    Optional<Bookmark> findByBoardIdAndUserId(long boardId, long userId);
}
