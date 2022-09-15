package com.example.juse.application.service;

import com.example.juse.application.entity.Application;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Service
public class ApplicationServiceStub implements ApplicationService {


    @Override
    public Application create(Application post) {
        return null;
    }
}
