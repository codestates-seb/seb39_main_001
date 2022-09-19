package com.example.juse.user.service;

import com.example.juse.helper.stubservice.StubService;
import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Profile("test")
@Service
public class UserServiceStub implements UserService {

    private final StubService stubService;
    private final UserRepository userRepository;

    @Override
    public User getJuse(long userId) {
        return stubService.getMyJuse();
    }

    @Override
    public User getProfile(long userId) {
        return stubService.getUser();
    }

    @Override
    public User update(User mappedObj) {
        return mappedObj;
    }

    @Override
    public void deleteAccount(long userId) {

    }

    @Override
    public User create(User mappedObj) {
        mappedObj.setId(1L);
        return mappedObj;
    }

    @Override
    public User findBySocialUserId(long socialUserId) {
        return null;
    }
}
