package com.example.juse.answer.service;

import com.example.juse.answer.entity.Answer;
import com.example.juse.answer.mapper.AnswerMapper;
import com.example.juse.answer.repository.AnswerRepository;
import com.example.juse.board.entity.Board;
import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.notification.service.NotificationService;
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
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Answer create(Answer mappedObj) {

        long questionId = mappedObj.getQuestion().getId();

        checkQuestionAlreadyAnswered(questionId);

        Question question = questionService.verifyQuestionById(questionId);
        Board board = question.getBoard();
        long userId = mappedObj.getUser().getId();

        if (!board.isCreatedBy(userId)) {
            throw new CustomRuntimeException(ExceptionCode.ANSWER_BOARD_WRITER_NOT_MATCHED);
        }

        User user = userService.verifyUserById(userId);
        mappedObj.addQuestion(question);
        mappedObj.addUser(user);

        Answer savedAnswer = answerRepository.save(mappedObj);
        notificationService.notifyNewAnswer(savedAnswer);
        return savedAnswer;
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

    public void checkQuestionAlreadyAnswered(long questionId) {
        if (answerRepository.findByQuestionId(questionId).isPresent()) {
            throw new CustomRuntimeException(ExceptionCode.QUESTION_ALREADY_ANSWERED);
        }
    }

}
