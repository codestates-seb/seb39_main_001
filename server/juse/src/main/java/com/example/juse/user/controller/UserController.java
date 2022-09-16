package com.example.juse.user.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/myjuse/{user-id}")
    public ResponseEntity<SingleResponseDto<UserResponseDto>> getMyjuse(
            @PathVariable("user-id") long userId
    ) {
        return null;
    }
}
