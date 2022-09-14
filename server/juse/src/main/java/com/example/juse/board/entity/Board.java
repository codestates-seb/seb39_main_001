package com.example.juse.board.entity;

import com.example.juse.apply.entity.Apply;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.like.entity.Like;
import com.example.juse.question.entity.Question;
import com.example.juse.tag.entity.BoardTag;
import com.example.juse.tag.entity.Tag;
import com.example.juse.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BOARDS")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    private int backend;
    private int frontend;
    private int designer;

    @Column(nullable = false)
    private int people;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private LocalDateTime startingDate;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private String onOffline;

    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.OPENING;

    private int view;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Question> questionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<BoardTag> boardTagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Like> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Apply> applyList = new ArrayList<>();


    @Getter
    public enum Type {

        PROJECT,
        STUDY,

    }

    @Getter
    public enum Status {

        OPENING,
        CLOSED

    }



}