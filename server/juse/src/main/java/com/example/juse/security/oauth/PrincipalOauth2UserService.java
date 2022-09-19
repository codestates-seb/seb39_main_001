package com.example.juse.security.config.oauth;

import com.example.juse.social.entity.SocialUser;
import com.example.juse.social.repository.SocialUserRepository;
import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private SocialUserRepository socialUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String img = oAuth2User.getAttribute("picture");
        String role = "ROLE_USER";

        SocialUser socialuser = socialUserRepository.findByEmail(email);

        if (socialuser == null) {
            socialuser = SocialUser.builder()
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .img(img)
                    .build();

            socialUserRepository.save(socialuser);
        }

        return new PrincipalDetails(socialuser, oAuth2User.getAttributes());
    }
}
