package com.example.juse.tag.repository;

import com.example.juse.tag.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {

    Optional<UserTag> findByTagNameAndUserId(String tagName, Long userId);
}
