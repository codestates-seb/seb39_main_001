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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/myjuse/{user-id}")
    public ResponseEntity<SingleResponseDto<UserResponseDto.MyJuse>> getMyjuse(
            @PathVariable("user-id") long userId
    ) {

        User foundUser = userService.getJuse(userId);
        UserResponseDto.MyJuse responseDto = userMapper.toMyJuseDtoFrom(foundUser);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<SingleResponseDto<UserResponseDto.Profile>> getProfile(
            @PathVariable("user-id") long userId
    ) {
        User userProfile = userService.getProfile(userId);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(userProfile);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity<SingleResponseDto<UserResponseDto.Profile>> patch(
            @PathVariable("user-id") long userId,
            @RequestBody UserRequestDto.Patch patchDto
    ) {
        patchDto.setId(userId);
        User mappedObj = userMapper.toEntityFrom(patchDto);
        User updatedEntity = userService.update(mappedObj);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{user-id}")
    public void deleteAccount(
            @PathVariable("user-id") long userId
    ) {
        userService.deleteAccount(userId);
    }

}
