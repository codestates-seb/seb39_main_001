package com.example.juse.user.service;

import com.example.juse.helper.stubservice.StubService;
import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Profile("real")
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getJuse(long userId) {
        return null;
    }

    @Override
    public User getProfile(long userId) {
        return null;
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
