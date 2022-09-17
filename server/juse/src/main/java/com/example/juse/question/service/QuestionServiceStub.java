package com.example.juse.question.service;

import com.example.juse.helper.stubservice.StubService;
import com.example.juse.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@RequiredArgsConstructor
@Service
public class QuestionServiceStub implements QuestionService {

    private final StubService stubService;

    @Override
    public Question create(Question post) {
        return stubService.getQuestion();
    }

    @Override
    public Question update(Question patch) {
        return patch;
    }

    @Override
    public void delete(long questionId, long userId) {

    }
}
