package com.example.juse.user.controller;

import com.example.juse.response.SingleResponseDto;
import com.example.juse.security.jwt.JwtTokenProvider;
import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.social.entity.SocialUser;
import com.example.juse.user.dto.UserPostDto;
import com.example.juse.user.entity.User;
import com.example.juse.user.mapper.UserMapper;
import com.example.juse.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper mapper;

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
                new SingleResponseDto<>(mapper.userResponseDto(user)), HttpStatus.OK);

    }
}
