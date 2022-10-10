package com.example.juse.social.repository;

import com.example.juse.social.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {

    SocialUser findByEmail(String email);

    Optional<SocialUser> findGitByEmail(String email);
}
