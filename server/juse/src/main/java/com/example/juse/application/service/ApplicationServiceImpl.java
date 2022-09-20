package com.example.juse.application.service;

import com.example.juse.application.entity.Application;
import com.example.juse.application.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public Application create(Application mappedObj) {
        return null;
    }

    @Override
    public Application update(Application mappedObj) {
        return null;
    }

    @Override
    public Application accept(long applicationId, long userId) {
        return null;
    }

    @Override
    public void deny(long applicationId, long userId) {

    }
}
