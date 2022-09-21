package com.example.juse.answer.service;

import com.example.juse.answer.entity.Answer;
import com.example.juse.answer.repository.AnswerRepository;
import com.example.juse.board.entity.Board;
import com.example.juse.question.entity.Question;
import com.example.juse.question.service.QuestionService;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;

    @Override
    public Answer create(Answer mappedObj) {

        Question question = questionService.verifyQuestionById(mappedObj.getQuestion().getId());
        Board board = question.getBoard();
        long userId = mappedObj.getUser().getId();

        if (!board.isCreatedBy(userId)) {
            throw new RuntimeException("게시글 작성자만 답글을 달 수 있습니다");
        }

        User user = userService.verifyUserById(userId);
        mappedObj.addQuestion(question);
        mappedObj.addUser(user);

        return answerRepository.save(mappedObj);
    }

    @Override
    public Answer update(Answer mappedObj) {
        return null;
    }

    @Override
    public void delete(long answerId, long userId) {

    }
}
