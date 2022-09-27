package com.example.juse.security.controller;

import com.example.juse.security.jwt.JwtTokenProvider;
import com.example.juse.security.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtTokenProvider jwtTokenProvider;


    @GetMapping("/token/ref")
    public String refToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Refresh");

        if (token != null && jwtTokenProvider.verifyToken(token)) {
            String email = jwtTokenProvider.getUid(token);
            TokenDto tokenDto = jwtTokenProvider.generateToken(email, "USER");

            response.addHeader("Auth", tokenDto.getAccessToken());
            response.addHeader("Refresh", tokenDto.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "new Token";
        }

        throw new RuntimeException();
    }
}
