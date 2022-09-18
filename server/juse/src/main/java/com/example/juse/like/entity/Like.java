package com.example.juse.like.entity;

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
@Table(name = "LIKES")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    //todo : user로 바뀌어야 한다. 컬럼이름을 바꾸든지 해서 구별하는 게 좋을 듯
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

}