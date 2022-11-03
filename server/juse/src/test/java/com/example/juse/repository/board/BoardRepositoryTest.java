package com.example.juse.repository.board;

import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.helper.stubservice.BoardStub;
import com.example.juse.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    @DisplayName("board query whose due date is after current date")
    public void queryTest() {
        BoardStub boardStub = new BoardStub(5, LocalDate.of(2022, 11, 1));

        userRepository.save(boardStub.getUser());
        boardRepository.saveAll(boardStub.getBoardList());

        List<Board> queriedBoardList = boardRepository.findCurrentlyOpened();

        assertThat(queriedBoardList).isNotEmpty();
        assertThat(queriedBoardList.get(0).getDueDate()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Board update test whose due date is after current date")
    public void updateQueryTest() {
        BoardStub boardStub = new BoardStub(10, LocalDate.of(2022, 10, 29));

        userRepository.save(boardStub.getUser());
        boardRepository.saveAll(boardStub.getBoardList());

        boardRepository.updateBoardAsClosed();

        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList.stream().anyMatch(board -> board.getStatus() == Board.Status.CLOSED)).isTrue();
        boardList.forEach(
                board -> {
                    System.out.println(board.getId() + " : " + board.getStatus());
                }
        );
    }
}
