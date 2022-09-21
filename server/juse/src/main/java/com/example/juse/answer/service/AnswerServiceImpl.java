package com.example.juse.answer.service;

import com.example.juse.answer.entity.Answer;
import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("real")
@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    @Override
    public Answer create(Answer mappedObj) {
        return null;
    }

    @Override
    public Answer update(Answer mappedObj) {
        return mappedObj;
    }

    @Override
    public void delete(long answerId, long userId) {

    }
}
