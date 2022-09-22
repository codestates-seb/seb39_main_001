package com.example.juse.user.service;

import com.example.juse.user.entity.User;

public interface UserService {

    User getJuse(long userId);

    User getProfile(long userId);

    User update(User mappedObj);

    void deleteAccount(long userId);

    User create(User mappedObj);

    User verifyUserById(long userId);
}
