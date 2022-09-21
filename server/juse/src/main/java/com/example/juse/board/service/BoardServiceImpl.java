package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Profile("real")
@Service
public class BoardServiceImpl implements BoardSerivice{

    @Override
    public Board create(Board post) {
        post.setId(1L);
        return post;
    }

    @Override
    public Board getBoard(long boardId) {
        return null;
    }

    @Override
    public Board update(Board patch) {
        return patch;
    }

    @Override
    public void delete(long boardId, long userId) {
    }

    @Override
    public Page<Board> getBoards(Pageable pageable) {

        return null;
    }
}
