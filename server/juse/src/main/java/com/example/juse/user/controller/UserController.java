package com.example.juse.user.controller;

import com.example.juse.response.SingleResponseDto;
import com.example.juse.security.jwt.JwtTokenProvider;
import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.social.entity.SocialUser;
import com.example.juse.user.dto.UserPostDto;
import com.example.juse.user.dto.UserRequestDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import com.example.juse.user.mapper.UserMapper;
import com.example.juse.user.repository.UserRepository;
import com.example.juse.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/join")
    public ResponseEntity joinUser() {
        return new ResponseEntity<>("noUser", HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity userJoin(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                   @RequestBody UserPostDto userPostDto) {

        User user = User.builder()
                .email(principalDetails.getUser().getEmail())
                .socialUser(SocialUser.builder().id(principalDetails.getUser().getId()).build())
                .introduction(userPostDto.getIntroduction())
                .nickname(userPostDto.getNickname())
                .portfolio(userPostDto.getPortfolio())
                .build();

        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @GetMapping("/user")
    public ResponseEntity getUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getUser());

        User user = userRepository.findByEmail(principalDetails.getUser().getEmail());

        return new ResponseEntity<>(
                new SingleResponseDto<>(userMapper.userResponseDto(user)), HttpStatus.OK);

    }

    @GetMapping("/myjuse/{user-id}")
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyJuse>> getMyjuse(
            @PathVariable("user-id") long userId
    ) {

        User foundUser = userService.getJuse(userId);
        UserResponseDto.MyJuse responseDto = userMapper.toMyJuseDtoFrom(foundUser);

        return new ResponseEntity<>(new com.example.juse.dto.SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.Profile>> getProfile(
            @PathVariable("user-id") long userId
    ) {
        User userProfile = userService.getProfile(userId);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(userProfile);

        return new ResponseEntity<>(new com.example.juse.dto.SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.Profile>> patch(
            @PathVariable("user-id") long userId,
            @RequestBody UserRequestDto.Patch patchDto
    ) {
        patchDto.setId(userId);
        User mappedObj = userMapper.toEntityFrom(patchDto);
        User updatedEntity = userService.update(mappedObj);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(updatedEntity);

        return new ResponseEntity<>(new com.example.juse.dto.SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{user-id}")
    public void deleteAccount(
            @PathVariable("user-id") long userId
    ) {
        userService.deleteAccount(userId);
    }

}
