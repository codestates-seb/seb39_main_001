package com.example.juse.user.service;

import com.example.juse.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User getJuse(long userId);

    User getProfile(long userId);

    User update(User mappedObj, MultipartFile profileImg);

    void deleteAccount(long userId);

    User create(User mappedObj);

    User verifyUserById(long userId);

    boolean isNicknameAvailable(String nickname);

    User createUser(User user, MultipartFile profileImg);
}
