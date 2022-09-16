package com.example.juse.user.service;

import com.example.juse.helper.stubservice.StubService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Profile("test")
@Service
public class UserServiceStub implements UserService {

    private final StubService stubService;
}
