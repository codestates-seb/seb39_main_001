package com.example.juse.question.entity;

import com.example.juse.answer.entity.Answer;
import com.example.juse.audit.Auditing;
import com.example.juse.board.entity.Board;
import com.example.juse.notification.entity.Notification;
import com.example.juse.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "QUESTIONS")
public class Question extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Setter
    @OneToOne(mappedBy = "question", cascade = {CascadeType.REMOVE})
    private Answer answer;

    @Builder.Default
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Notification> notificationList = new ArrayList<>();

    public void addBoard(Board board) {
        this.board = board;
        if (!this.board.getQuestionList().contains(this)) {
            this.board.getQuestionList().add(this);
        }
    }

    public void addUser(User user) {
        this.user = user;
        if (!this.user.getQuestionList().contains(this)) {
            this.user.getQuestionList().add(this);
        }
    }

    public boolean isCreatedBy(long userId) {
        return this.user.getId() == userId;
    }
}