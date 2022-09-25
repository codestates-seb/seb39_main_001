package com.example.juse.social.repository;

import com.example.juse.social.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {

    SocialUser findByEmail(String email);
}
