package com.example.juse.board.repository;

import com.example.juse.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b " +
            "JOIN b.boardTagList bt " +
            "WHERE b.status IN :status " +
            "AND b.type IN :type " +
            "AND bt.tag.name IN :tag " +
            "AND b.period IN :period"
    )
    Page<Board> findWithParameters(
            Pageable pageable,
            @Param("status") List<Board.Status> status,
            @Param("type") List<Board.Type> type,
            @Param("tag") List<String> tag,
            @Param("period") List<String> period);

//    nativeQuery = true,
//    value =
//            "SELECT * FROM BOARDS " +
//            "JOIN BOARDS_TAGS bt " +
//            "ON BOARDS.ID = bt.BOARD_ID " +
//            "JOIN TAGS t " +
//            "ON t.ID = bt.TAG_ID " +
//            "WHERE BOARDS.TYPE IN :type " +
//            "AND t.NAME IN :tag " +
//            "AND BOARDS.STATUS IN :status " +
//            "AND BOARDS.PERIOD IN :period " +
//            "ORDER BY BOARDS.CREATED_AT DESC"
}

