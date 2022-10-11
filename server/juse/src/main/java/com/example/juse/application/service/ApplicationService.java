package com.example.juse.application.service;

import com.example.juse.application.entity.Application;

public interface ApplicationService {

    Application create(Application mappedObj);

    Application accept(long applicationId, long userId);

    void decline(long applicationId, long userId);

}
