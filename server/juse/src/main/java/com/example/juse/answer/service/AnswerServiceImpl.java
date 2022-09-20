package com.example.juse.answer.service;

import com.example.juse.answer.entity.Answer;
import com.example.juse.answer.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public Answer create(Answer mappedObj) {
        return null;
    }

    @Override
    public Answer update(Answer mappedObj) {
        return null;
    }

    @Override
    public void delete(long answerId, long userId) {

    }
}
