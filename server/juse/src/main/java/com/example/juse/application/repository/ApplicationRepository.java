package com.example.juse.application.repository;

import com.example.juse.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByUserIdAndBoardId(long userId, long boardId);
}
