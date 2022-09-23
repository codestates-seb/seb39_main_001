package com.example.juse.answer.service;

import com.example.juse.answer.entity.Answer;
import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"test"})
@RequiredArgsConstructor
@Service
public class AnswerServiceStub implements AnswerService {

    private final StubService stubService;

    @Override
    public Answer create(Answer mappedObj) {
        return stubService.getAnswer();
    }

    @Override
    public Answer update(Answer mappedObj) {
        return mappedObj;
    }

    @Override
    public void delete(long answerId, long userId) {

    }

    @Override
    public Answer verifyAnswerById(long answerId) {
        return null;
    }
}
