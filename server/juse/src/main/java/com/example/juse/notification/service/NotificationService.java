package com.example.juse.notification.service;

import com.example.juse.answer.entity.Answer;
import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.repository.NotificationRepository;
import com.example.juse.question.entity.Question;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyBoardClosed(Board board) {
        board.getApplicationList().forEach(
                application -> {
                    Notification notification = new Notification();
                    notification.setType(Notification.Type.CLOSE);
                    notification.setBoard(board);
                    notification.addUsers(board.getUser(), application.getUser());
                    notificationRepository.save(notification);
                }
        );

        log.info("{} 번 게시글 작성자 {}번 사용자가 해당 게시글에 지원한 {} 명 유저에게 모집이 마감되었다고 알림을 보냄",
                board.getId(), board.getUser().getId(), board.getApplicationList().size());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyNewQuestion(Question question) {
        Board board = question.getBoard();
        User sender = question.getUser();
        User receiver = board.getUser();

        Notification notification = new Notification();
        notification.setType(Notification.Type.QUESTION);
        notification.setBoard(board);
        notification.addUsers(sender, receiver);
        notification.setQuestion(question);
        notificationRepository.save(notification);

        log.info("{} 번 유저가 작성한 {} 번 게시글에 {}번 유저가 문의를 남김",
                receiver.getId(), board.getId(), sender.getId());
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void notifyNewAnswer(Answer answer) {

        Question question = answer.getQuestion();
        Board board = question.getBoard();
        User sender = board.getUser();
        User receiver = question.getUser();

        Notification notification = new Notification();
        notification.setType(Notification.Type.ANSWER);
        notification.setAnswer(answer);
        notification.setBoard(board);
        notification.addUsers(sender, receiver);
        notificationRepository.save(notification);

        log.info("{} 번 게시글에 {} 번 사용자가 문의한 글에 답변이 달렸습니다",
                board.getId(), receiver.getId());
    }
}
