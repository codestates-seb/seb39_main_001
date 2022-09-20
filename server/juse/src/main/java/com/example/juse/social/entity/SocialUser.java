package com.example.juse.social.entity;

import com.example.juse.user.entity.User;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SOCIAL_USERS")
@Setter
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

    @Setter
    @OneToOne(mappedBy = "socialUser")
    @JsonIgnore
    private User user;


}
