package com.example.juse.answer.service;

import com.example.juse.answer.entity.Answer;

public interface AnswerService {

    Answer create(Answer mappedObj);

    Answer update(Answer mappedObj);

    void delete(long answerId, long userId);
}
