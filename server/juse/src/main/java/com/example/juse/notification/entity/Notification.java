package com.example.juse.notification.entity;

import com.example.juse.answer.entity.Answer;
import com.example.juse.application.entity.Application;
import com.example.juse.audit.Auditing;
import com.example.juse.board.entity.Board;
import com.example.juse.like.entity.Like;
import com.example.juse.question.entity.Question;
import com.example.juse.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "NOTIFICATIONS")
@Entity
public class Notification extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID")
    private Application application;

    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private Notification.Type type;

    public void addUsers(User sender, User receiver) {
        this.sender = sender;
        if (this.sender.getSentNotificationList().contains(this)) {
            this.sender.getSentNotificationList().add(this);
        }

        this.receiver = receiver;
        if (this.receiver.getReceivedNotificationList().contains(this)) {
            this.receiver.getReceivedNotificationList().add(this);
        }
    }

    @Getter
    public enum Type {
        APPLICATION,
        ACCEPT,
        DECLINE,
        CLOSE,
        QUESTION,
        ANSWER,
        UPCOMING,
        LIKE
    }

}