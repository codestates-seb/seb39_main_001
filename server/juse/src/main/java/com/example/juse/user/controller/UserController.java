package com.example.juse.user.controller;

import com.example.juse.dto.SingleResponseDto;
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
        SocialUser socialUser = principalDetails.getSocialUser();
        mappedObj.setEmail(principalDetails.getSocialUser().getEmail());
        mappedObj.addSocialUser(socialUser);

        User createdUser = userService.create(mappedObj);
        UserResponseDto.MyProfile response = userMapper.toMyProfileDtoFrom(createdUser);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);

    }

    @GetMapping("/myjuse")
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyJuse>> getMyjuse(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        System.out.println("userId = " + principalDetails.getSocialUser().getUser().getId());

        User foundUser = userService.getJuse(userId);
        UserResponseDto.MyJuse responseDto = userMapper.toMyJuseDtoFrom(foundUser);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyProfile>> getProfile(
            @AuthenticationPrincipal PrincipalDetails principalDetails

    ) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        User userProfile = userService.getProfile(userId);
        UserResponseDto.MyProfile responseDto = userMapper.toMyProfileDtoFrom(userProfile);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{other-user-id}")
    public ResponseEntity<SingleResponseDto<UserResponseDto.Profile>> getOtherUserProfile(
            @PathVariable("other-user-id") long userId
    ) {
        User userProfile = userService.getProfile(userId);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(userProfile);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyProfile>> patch(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody UserRequestDto.Patch patchDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        SocialUser socialUser = principalDetails.getSocialUser();
        patchDto.setId(userId);
        patchDto.setEmail(principalDetails.getSocialUser().getEmail());

        User mappedObj = userMapper.toEntityFrom(patchDto);
        mappedObj.addSocialUser(socialUser);
        User updatedEntity = userService.update(mappedObj);
        UserResponseDto.MyProfile responseDto = userMapper.toMyProfileDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteAccount(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();

        userService.deleteAccount(userId);
    }

    @GetMapping("/nicknames")
    public ResponseEntity<SingleResponseDto<Boolean>> findNicknames(
            @RequestParam("q") String nickname
    ) {
        Boolean response = userService.isNicknameAvailable(nickname);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
