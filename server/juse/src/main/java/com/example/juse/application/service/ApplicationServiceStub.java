package com.example.juse.application.service;

import com.example.juse.application.entity.Application;
import com.example.juse.application.repository.ApplicationRepository;
import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"test"})
@RequiredArgsConstructor
@Service
public class ApplicationServiceStub implements ApplicationService {

    private final StubService stubService;

    private final ApplicationRepository applicationRepository;

    @Override
    public Application create(Application mappedObj) {

//        return stubService.getApplication();
        return applicationRepository.save(mappedObj);
    }

    @Override
    public Application create(Application mappedObj, long BoardId) {
        return null;
    }

    @Override
    public Application update(Application mappedObj) {
        return null;
    }

    @Override
    public Application accept(long applicationId, long userId) {
        return stubService.getAcceptedApplication();
    }

    @Override
    public void deny(long applicationId, long userId) {

    }
}
