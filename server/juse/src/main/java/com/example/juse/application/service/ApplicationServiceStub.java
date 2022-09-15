package com.example.juse.application.service;

import com.example.juse.application.entity.Application;
import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@RequiredArgsConstructor
@Service
public class ApplicationServiceStub implements ApplicationService {

    private final StubService stubService;

    @Override
    public Application create(Application mappedObj) {

        return stubService.getApplicationStub();
    }
}
