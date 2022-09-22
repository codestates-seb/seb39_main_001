package com.example.juse.board.repository;

import com.example.juse.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b From Board b " +
            "JOIN b.boardTagList bt " +
            "WHERE bt.tag.name " +
            "IN :tag " +
            "AND b.period IN :period")
    Page<Board> findWithFilter(
            Pageable pageable,
            @Param("tag") Collection<String> tag,
            @Param("period") Collection<String> period);
}
