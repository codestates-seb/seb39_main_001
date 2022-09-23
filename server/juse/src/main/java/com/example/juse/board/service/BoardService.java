package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import com.example.juse.helper.filterings.FilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Board create(Board post);

    Board getBoard(long boardId);

    Board update(Board patch);

    void delete(long boardId, long userId);

    Page<Board> getBoards(Pageable pageable, FilterOptions filterOptions);

    Board verifyBoardById(long boardId);
}
