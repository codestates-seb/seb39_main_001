package com.example.juse.user.entity;

import com.example.juse.answer.entity.Answer;
import com.example.juse.application.entity.Application;
import com.example.juse.board.entity.Board;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.like.entity.Like;
import com.example.juse.question.entity.Question;
import com.example.juse.tag.entity.UserTag;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Byte[] profileImage;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String email;

    private String portfolio;
    private int liked;

    @Column(nullable = false)
    private String nickname;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "whoLikes")
    private List<Like> whoLikesList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "whoIsLiked")
    private List<Like> whoIsLikeList = new ArrayList<>();


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
    private List<Application> applicationList = new ArrayList<>();

    public List<Board> getMyBookmarkList() {
        return this.bookmarkList.stream().map(Bookmark::getBoard).collect(Collectors.toList());
    }

    public List<Board> getMyParticipationList() {
        return this.applicationList.stream().filter(Application::isAccepted).map(Application::getBoard).collect(Collectors.toList());
    }

    public List<Board> getMyApplicationList() {
        return this.applicationList.stream().filter(application -> !application.isAccepted()).map(Application::getBoard).collect(Collectors.toList());
    }

    public List<String> getSkillStackTags() {
        return this.userTagList.stream().map(userTag -> userTag.getTag().getName()).collect(Collectors.toList());
    }

    public List<User> getMyUserList() {
        return this.whoLikesList.stream().map(Like::getWhoIsLiked).collect(Collectors.toList());
    }

}