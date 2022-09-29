package com.example.juse.application.service;

import com.example.juse.application.entity.Application;

public interface ApplicationService {

    Application create(Application mappedObj);

//    Application create(Application mappedObj, long BoardId);

    Application update(Application mappedObj);

    Application accept(long applicationId, long userId);

    void deny(long applicationId, long userId);

}
