package com.example.juse.answer.service;

import com.example.juse.answer.entity.Answer;
import com.example.juse.answer.mapper.AnswerMapper;
import com.example.juse.answer.repository.AnswerRepository;
import com.example.juse.board.entity.Board;
import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.question.entity.Question;
import com.example.juse.question.service.QuestionService;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerMapper answerMapper;

    @Override
    @Transactional
    public Answer create(Answer mappedObj) {

        Question question = questionService.verifyQuestionById(mappedObj.getQuestion().getId());
        Board board = question.getBoard();
        long userId = mappedObj.getUser().getId();

        if (!board.isCreatedBy(userId)) {
            throw new CustomRuntimeException(ExceptionCode.ANSWER_BOARD_WRITER_NOT_MATCHED);
        }

        User user = userService.verifyUserById(userId);
        mappedObj.addQuestion(question);
        mappedObj.addUser(user);

        return answerRepository.save(mappedObj);
    }

    @Override
    public Answer update(Answer mappedObj) {

        Answer answer = verifyAnswerById(mappedObj.getId());
        long userId = mappedObj.getUser().getId();

        if (!answer.isCreatedBy(userId)) {
            throw new CustomRuntimeException(ExceptionCode.ANSWER_WRITER_NOT_MATCHED);
        }

        answerMapper.updateEntityFromSource(answer, mappedObj);

        return answerRepository.save(answer);
    }

    @Override
    public void delete(long answerId, long userId) {
        Answer answer = verifyAnswerById(answerId);

        if (!answer.isCreatedBy(userId)) {
            throw new CustomRuntimeException(ExceptionCode.ANSWER_WRITER_NOT_MATCHED);
        }

        answerRepository.delete(answer);

    }

    @Override
    public Answer verifyAnswerById(long answerId) {
        return answerRepository.findById(answerId).orElseThrow(
                () -> new CustomRuntimeException(ExceptionCode.ANSWER_NOT_FOUND)
        );
    }

}
