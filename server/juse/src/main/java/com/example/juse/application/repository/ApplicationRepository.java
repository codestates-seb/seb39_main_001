package com.example.juse.application.repository;

import com.example.juse.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // User Id를 통해 Application 을 찾아온다.
    Optional<Application> findByUserIdAndBoardId(long userId, long boardId);

}
