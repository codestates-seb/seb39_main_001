package com.example.juse.tag.repository;

import com.example.juse.tag.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {


    @Query("SELECT bt FROM BoardTag bt JOIN bt.tag t where t.name = :tagName and bt.board.id = :boardId")
    Optional<BoardTag> findByTagNameAndBoardId(
            @Param("tagName") String tagName,
            @Param("boardId") Long boardId
    );
}
