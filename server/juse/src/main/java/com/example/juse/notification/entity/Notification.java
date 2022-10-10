package com.example.juse.notification.entity;

import com.example.juse.answer.entity.Answer;
import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    @Column(nullable = true)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    @Column(nullable = true)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID")
    @Column(nullable = true)
    private Application application;

    @Setter
    private boolean isRead;


    public void addUser(User user) {
        this.user = user;
        if (this.user.getNotificationList().contains(this)) {
            this.user.getNotificationList().add(this);
        }
    }
}