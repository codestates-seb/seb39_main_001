package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import com.example.juse.helper.stubservice.StubService;
import com.example.juse.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Profile("test")
@Service
public class BoardServiceStub implements BoardSerivice{

    private final StubService stubService;

    @Override
    public Board create(Board post) {
        return stubService.getBoard();
    }

    @Override
    public Board getBoard(long boardId) {
        return stubService.getBoard();
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
        List<Board> content = List.of(
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard(),
                stubService.getBoard()
        );

        return new PageImpl<>(content, pageable, content.size());
    }
}
