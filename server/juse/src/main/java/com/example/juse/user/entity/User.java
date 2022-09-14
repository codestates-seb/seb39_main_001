package com.example.juse.user.entity;

import com.example.juse.answer.entity.Answer;
import com.example.juse.apply.entity.Apply;
import com.example.juse.board.entity.Board;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.like.entity.Like;
import com.example.juse.question.entity.Question;
import com.example.juse.tag.entity.UserTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Byte[] profileImage;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String email;

    private String portfolio;

    @Column(nullable = false)
    private String nickname;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Like> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserTag> userTagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Question> questionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Answer> answerList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Apply> applyList = new ArrayList<>();
}