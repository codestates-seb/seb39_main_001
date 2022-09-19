package com.example.juse.question.service;

import com.example.juse.question.entity.Question;

public interface QuestionService {

    Question create(Question post);

    Question update(Question patch);

    void delete(long questionId, long userId);

}
