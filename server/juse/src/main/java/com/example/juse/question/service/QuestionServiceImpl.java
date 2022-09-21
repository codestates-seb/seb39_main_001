package com.example.juse.question.service;

import com.example.juse.helper.stubservice.StubService;
import com.example.juse.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("real")
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {


    @Override
    public Question create(Question post) {
        return null;
    }

    @Override
    public Question update(Question patch) {
        return patch;
    }

    @Override
    public void delete(long questionId, long userId) {

    }
}
