package com.example.juse.social.entity;

import com.example.juse.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SOCIAL_USERS")
public class SocialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String img;

    private String email;

    private String name;

    private String role;

    private String provider;

    private String providerId;

    @OneToOne(mappedBy = "socialUser")
    @JsonIgnore
    private User user;


}
