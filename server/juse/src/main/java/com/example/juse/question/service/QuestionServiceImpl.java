package com.example.juse.question.service;

import com.example.juse.question.entity.Question;
import com.example.juse.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService{

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
}
