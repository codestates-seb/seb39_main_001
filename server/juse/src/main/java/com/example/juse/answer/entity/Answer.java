package com.example.juse.answer.entity;

import com.example.juse.audit.Auditing;
import com.example.juse.question.entity.Question;
import com.example.juse.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ANSWERS")
public class Answer extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public void addQuestion(Question question) {
        this.question = question;
        this.question.setAnswer(this);
    }

    public void addUser(User user) {
        this.user = user;
        if (!this.user.getAnswerList().contains(this)) {
            this.user.getAnswerList().add(this);
        }
    }

    public boolean isCreatedBy(long userId) {
        return this.user.getId() == userId;
    }
}