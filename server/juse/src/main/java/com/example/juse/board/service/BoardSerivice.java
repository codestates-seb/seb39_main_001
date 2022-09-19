package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSerivice {

    Board create(Board post);

    Board getBoard(long boardId);

    Board update(Board patch);

    void delete(long boardId, long userId);

    Page<Board> getBoards(Pageable pageable);
}
