package com.example.juse.user.controller;

import com.example.juse.board.entity.Board;
import com.example.juse.response.SingleResponseDto;
import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.social.entity.SocialUser;
import com.example.juse.user.dto.UserRequestDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import com.example.juse.user.mapper.UserMapper;
import com.example.juse.user.repository.UserRepository;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
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
                                   @RequestBody UserRequestDto.Post userPostDto) {

        User mappedObj = userMapper.toEntityFrom(userPostDto);

        mappedObj.setEmail(principalDetails.getSocialUser().getEmail());

        SocialUser socialUser = principalDetails.getSocialUser();
        mappedObj.addSocialUser(socialUser);

        UserResponseDto.Profile response = userMapper.toProfileDtoFrom(userRepository.save(mappedObj));

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);

    }

    @GetMapping("/myjuse")
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyJuse>> getMyjuse(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        System.out.println("userId = " + principalDetails.getSocialUser().getUser().getId());

        User foundUser = userService.getJuse(userId);


        UserResponseDto.MyJuse responseDto = userMapper.toMyJuseDtoFrom(foundUser);

        return new ResponseEntity<>(new com.example.juse.dto.SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.Profile>> getProfile(
            @AuthenticationPrincipal PrincipalDetails principalDetails

    ) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        User userProfile = userService.getProfile(userId);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(userProfile);

        return new ResponseEntity<>(new com.example.juse.dto.SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.Profile>> patch(
            @AuthenticationPrincipal PrincipalDetails principalDetails,

            @RequestBody UserRequestDto.Patch patchDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();

        patchDto.setId(userId);
        User mappedObj = userMapper.toEntityFrom(patchDto);
        User updatedEntity = userService.update(mappedObj);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(updatedEntity);

        return new ResponseEntity<>(new com.example.juse.dto.SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteAccount(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();

        userService.deleteAccount(userId);
    }

}
