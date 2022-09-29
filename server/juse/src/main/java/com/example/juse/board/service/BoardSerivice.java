package com.example.juse.board.service;

import com.example.juse.board.entity.Board;

public interface BoardSerivice {

    Board create(Board post);

    Board getBoard(long boardId);

    Board update(Board patch);

    void delete(long boardId, long userId);
}
