package com.example.juse.user.controller;

import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import com.example.juse.user.mapper.UserMapper;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserTestController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/profileTest")
    public UserResponseDto.MyProfile testmethod(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        User user = userService.verifyUserById(principalDetails.getSocialUser().getUser().getId());
        return userMapper.toMyProfileDtoFrom(user);
    }
}
