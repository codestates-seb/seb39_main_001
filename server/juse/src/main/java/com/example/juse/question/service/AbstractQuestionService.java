package com.example.juse.question.service;

import com.example.juse.board.service.BoardService;
import com.example.juse.question.entity.Question;
import com.example.juse.question.repository.QuestionRepository;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public abstract class AbstractQuestionService implements QuestionService{

    private final BoardService boardService;
    private final UserService userService;
    private final QuestionRepository questionRepository;

    @Override
    public Question create(Question post) {
        return null;
    }

    @Override
    public Question update(Question patch) {
        return null;
    }

    @Override
    public void delete(long questionId, long userId) {

    }

    @Override
    public Question findById(long questionId) {
        return questionRepository.findById(questionId).orElseThrow(NoSuchElementException::new);
    }
}
