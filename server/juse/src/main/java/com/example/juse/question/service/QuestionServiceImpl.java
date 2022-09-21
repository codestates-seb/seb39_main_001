package com.example.juse.question.service;

import com.example.juse.board.entity.Board;
import com.example.juse.board.service.BoardService;
import com.example.juse.question.entity.Question;
import com.example.juse.question.repository.QuestionRepository;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("plain")
@Service
public class QuestionServiceImpl extends AbstractQuestionService{


    @Override
    public Question create(Question post) {
        long boardId = post.getBoard().getId();
        long userId = post.getUser().getId();

        Board board = boardService.verifyBoardById(boardId);
        User user = board.getUser();

        post.addBoard(board);
        post.addUser(user);

        return questionRepository.save(post);
    }

    @Override
    public Question update(Question patch) {
        Question question =
        return null;
    }

    @Override
    public void delete(long questionId, long userId) {

    }
}
