package com.example.juse.board.entity;

import com.example.juse.application.entity.Application;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.like.entity.Like;
import com.example.juse.question.entity.Question;
import com.example.juse.tag.entity.BoardTag;
import com.example.juse.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private String title;

    @Column(nullable = false)
    private String content;

    private int backend;
    private int frontend;
    private int designer;
    private int etc;
    private int bookmarks;
    private int liked;
    private int views;

    @Column(nullable = false)
    private int people;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalDate startingDate;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private String onOffline;

    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.OPENING;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

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

//    @Builder.Default
//    @OneToMany(mappedBy = "board")
//    private List<Like> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Application> applicationList = new ArrayList<>();

    public List<String> getTagNames() {
        return this.boardTagList.stream().map(boardTag -> boardTag.getTag().getName()).collect(Collectors.toList());
    }

    public int getLikedCount() {
        return this.bookmarkList.size();
    }

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