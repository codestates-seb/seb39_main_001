package com.example.juse.user.repository;

import com.example.juse.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

//    @Query("select u from User u where u.nickname = :nickname")
    Optional<User> findByNickname(String nickname);

}
