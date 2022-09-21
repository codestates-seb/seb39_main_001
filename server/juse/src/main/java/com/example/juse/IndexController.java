package com.example.juse;

import com.example.juse.security.config.oauth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        try {
            if(principalDetails.getUsername() != null) {
                model.addAttribute("username", principalDetails.getName());
            }
        } catch (NullPointerException e) {}
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/join")
    public @ResponseBody String join() {
        return "join";
    }

    @GetMapping("/loginTest")
    public @ResponseBody String loginTest(Authentication authentication,
                                          @AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println("======================== login test ======================");
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication = " + user.getAttributes());
        System.out.println("oAuth2User = " + oAuth2User.getAttributes());
        System.out.println("oAuth2UserId = " + oAuth2User.getAttributes());

        return "login Test";
    }
}
