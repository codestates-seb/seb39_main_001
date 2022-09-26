package com.example.juse.board.repository;

import com.example.juse.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT DISTINCT b FROM Board b " +
            "JOIN b.boardTagList bt " +
            "JOIN bt.tag t " +
            "WHERE (:status IS NULL OR b.status = :status) " +
            "AND (:type IS NULL OR b.type = :type) " +
            "AND (:period IS NULL OR b.period IN :period) " +
            "AND (COALESCE(:tag) IS NULL OR t.name IN :tag) " +
            "ORDER BY b.createdAt desc"
    )
    Page<Board> findWithParameters(
            Pageable pageable,
            @Param("status") Board.Status status,
            @Param("type") Board.Type type,
            @Param("tag") List<String> tag,
            @Param("period") List<String> period);

}

