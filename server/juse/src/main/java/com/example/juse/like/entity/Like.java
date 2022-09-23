package com.example.juse.like.entity;

import com.example.juse.board.entity.Board;
import com.example.juse.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LIKES")
@ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WHO_LIKES")
    private User whoLikes;

    //todo : user로 바뀌어야 한다. 컬럼이름을 바꾸든지 해서 구별하는 게 좋을 듯
    @ManyToOne
    @JoinColumn(name = "WHO_IS_LIKED")
    private User whoIsLiked;

}