package com.example.juse.user.service;

import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        return null;
    }

    @Override
    public void deleteAccount(long userId) {

    }

    @Override
    public User create(User mappedObj) {
        return null;
    }

    @Override
    public User findBySocialUserId(long socialUserId) {
        return null;
    }
}
