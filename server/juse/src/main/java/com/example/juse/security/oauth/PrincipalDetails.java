package com.example.juse.security.oauth;

import com.example.juse.social.entity.SocialUser;
import com.example.juse.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@Getter
@AllArgsConstructor
public class PrincipalDetails implements  OAuth2User {

    private SocialUser user;
    private Map<String, Object> attributes;


    @Autowired
    public PrincipalDetails(SocialUser socialUser) {
        this.user = socialUser;
    }
//    @Autowired
//    public PrincipalDetails(SocialUser user, Map<String, Object> attributes) {
//        this.user = user;
//        this.attributes = attributes;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collection;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

}
