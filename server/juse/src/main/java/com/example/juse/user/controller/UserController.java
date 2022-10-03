package com.example.juse.user.controller;

import com.example.juse.dto.SingleResponseDto;
import com.example.juse.exception.validator.NotEmptyToken;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@RestController
@Validated
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

    @PostMapping(value = "/join", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity userJoin(@AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
                                   @RequestPart @Valid UserRequestDto.Post userPostDto,
                                   @RequestPart(required = false) MultipartFile profileImg) {

        User mappedObj = userMapper.toEntityFrom(userPostDto);
        SocialUser socialUser = principalDetails.getSocialUser();
        mappedObj.setEmail(principalDetails.getSocialUser().getEmail());
        mappedObj.addSocialUser(socialUser);

        User createdUser = userService.createUser(mappedObj, profileImg);
        UserResponseDto.MyProfile response = userMapper.toMyProfileDtoFrom(createdUser);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);

    }

    @GetMapping("/myjuse")
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyJuse>> getMyjuse(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        System.out.println("userId = " + principalDetails.getSocialUser().getUser().getId());

        User foundUser = userService.getJuse(userId);
        UserResponseDto.MyJuse responseDto = userMapper.toMyJuseDtoFrom(foundUser);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyProfile>> getProfile(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails

    ) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        User userProfile = userService.getProfile(userId);
        UserResponseDto.MyProfile responseDto = userMapper.toMyProfileDtoFrom(userProfile);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{other-user-id}")
    public ResponseEntity<SingleResponseDto<UserResponseDto.Profile>> getOtherUserProfile(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("other-user-id") @Positive long userId
    ) {
        long myId = principalDetails.getSocialUser().getUser().getId();
        User userProfile = userService.getProfile(userId);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(userProfile);

        if (userProfile.isLikedBy(myId)) {
            responseDto.setLikedByMe(true);
        }

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyProfile>> patch(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @RequestPart @Valid UserRequestDto.Patch patchDto,
            @RequestPart(required = false) MultipartFile profileImg
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        SocialUser socialUser = principalDetails.getSocialUser();
        patchDto.setId(userId);
        patchDto.setEmail(principalDetails.getSocialUser().getEmail());

        User mappedObj = userMapper.toEntityFrom(patchDto);
        mappedObj.addSocialUser(socialUser);
        User updatedEntity = userService.update(mappedObj, profileImg);
        UserResponseDto.MyProfile responseDto = userMapper.toMyProfileDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteAccount(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails
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
