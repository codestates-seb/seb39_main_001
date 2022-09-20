package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardSerivice{

    private final BoardRepository boardRepository;

    @Override
    public Board create(Board post) {
        return null;
    }

    @Override
    public Board getBoard(long boardId) {
        return null;
    }

    @Override
    public Board update(Board patch) {
        return null;
    }

    @Override
    public void delete(long boardId, long userId) {

    }

    @Override
    public Page<Board> getBoards(Pageable pageable) {
        return null;
    }
}
