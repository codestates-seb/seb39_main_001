package com.example.juse.security.handler;

import com.example.juse.security.jwt.JwtTokenProvider;
import com.example.juse.security.jwt.TokenDto;
import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.social.entity.SocialUser;
import com.example.juse.social.repository.SocialUserRepository;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {


    private final JwtTokenProvider jwtTokenProvider;

    private final SocialUserRepository socialUserRepository;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth 로그인 성공 시 소셜 로그인 정보를 받아와 해당 객체로 저장한다.
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        System.out.println("principalDetails.toString() = " + principalDetails.toString());

        // 받아온 객체를 Map 형식으로 저장.
        Map<String, Object> attributes = principalDetails.getAttributes();

        System.out.println("attributes.toString() = " + attributes.toString());

        // 토큰을 발급받기 위해 SocialUser 객체로 이름과 email을 받아온다.
        SocialUser socialUser = SocialUser.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();

        System.out.println("socialUser.toString() = " + socialUser.toString());

        // email을 기준으로 토큰 발급
        TokenDto tokenDto = jwtTokenProvider.generateToken(socialUser.getEmail(), "ROLE_USER");

        response.setHeader("Auth", tokenDto.getAccessToken());
        response.setHeader("Refresh", tokenDto.getRefreshToken());

        System.out.println("socialUser.getEmail() = " + socialUser.getEmail());

        // 최초 로그인 시 회원가입하기 위해 이동.
        if (userRepository.findByEmail(socialUser.getEmail()) == null) {
            response.sendRedirect("/users/join");
        }
        else {
            response.sendRedirect("/index");
        }
    }
}
