package com.example.juse.apply.entity;

import com.example.juse.board.entity.Board;
import com.example.juse.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "APPLIES")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String position;

    private boolean isAccepted;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}