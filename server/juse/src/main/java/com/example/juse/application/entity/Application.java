package com.example.juse.application.entity;

import com.example.juse.audit.Auditing;
import com.example.juse.board.entity.Board;
import com.example.juse.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "APPLICATIONS")
@ToString
public class Application extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String position;

    @Setter
    private boolean isAccepted;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public void checkApplicationWriter(long userId) {
        if (this.getBoard().getUser().getId() != userId) {
            throw new RuntimeException("모집자만이 수락 및 거절 할 수 있습니다.");
        }
    }
}