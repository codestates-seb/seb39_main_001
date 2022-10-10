package com.example.juse.notification.service;

import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.repository.NotificationRepository;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final UserService userService;
    private final NotificationRepository notificationRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyNewApplication(Board board, Application application) {

        Long writerId = board.getUser().getId();
        User writer = userService.verifyUserById(writerId);

        Long applicantId = application.getUser().getId();
        User applicant = userService.verifyUserById(applicantId);

        Notification notification = new Notification();
        notification.setApplication(application);
        notification.setType(Notification.Type.APPLICATION);
        notification.addUsers(applicant, writer);

        notificationRepository.save(notification);

        log.info("{}번 사용자가 {}번 사용자가 작성한 {}번 게시글의 포지션 {} 에 {}에 지원했음",
                applicantId, writerId, board.getId(), application.getPosition(), application.getCreatedAt());

    }
}
