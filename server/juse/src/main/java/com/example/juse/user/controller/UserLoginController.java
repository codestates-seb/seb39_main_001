package com.example.juse.user.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.user.dto.UserRequestDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import com.example.juse.user.mapper.UserMapper;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserLoginController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/join")
    public ResponseEntity<SingleResponseDto<UserResponseDto.MyProfile>> join(
            @RequestBody UserRequestDto.Post postDto
    ) {
        User mappedObj = userMapper.toEntityFrom(postDto);
        User createdEntity = userService.create(mappedObj);
        UserResponseDto.MyProfile responseDto = userMapper.toMyProfileDtoFrom(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);

    }

}
