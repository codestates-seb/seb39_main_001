package com.example.juse.board.repository;

import com.example.juse.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
//
//    Page<Board> findByFilterOptions(Pageable pageable,
//                                    @Param("type") String type,
//                                    @Param("status") String status);
}
