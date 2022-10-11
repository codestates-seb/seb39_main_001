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
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

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

        User applicant = application.getUser();

        Notification notification = new Notification();
        notification.setApplication(application);
        notification.setBoard(board);
        notification.setType(Notification.Type.APPLICATION);
        notification.addUsers(applicant, writer);

        notificationRepository.save(notification);

        log.info("{}번 사용자가 {}번 사용자가 작성한 {}번 게시글의 포지션 {} 에 {}에 지원했음",
                applicant.getId(), writerId, board.getId(), application.getPosition(), application.getCreatedAt());

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyApplicationAccepted(Board board, Application application) {

        User writer = board.getUser();
        User accepted = application.getUser();

        Notification notification = new Notification();
        notification.setType(Notification.Type.ACCEPT);
        notification.addUsers(writer, accepted);
        notification.setApplication(application);
        notification.setBoard(board);

        notificationRepository.save(notification);

        log.info("{}번 게시글 {} 포지션에 지원한 {}번 사용자의 지원이 수락되었습니다",
                board.getId(), application.getPosition(), accepted.getId());

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyApplicationDeclined(Board board, Application application) {

        User writer = board.getUser();
        User declined = application.getUser();

        Notification notification = new Notification();
        notification.setType(Notification.Type.DECLINE);
        notification.addUsers(writer, declined);
        notification.setApplication(application);
        notification.setBoard(board);

        notificationRepository.save(notification);

        log.info("{}번 게시글 {} 포지션에 지원한 {}번 사용자의 지원이 거절되었습니다",
                board.getId(), application.getPosition(), declined.getId());

    }

}
