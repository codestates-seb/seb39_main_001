package com.example.juse.user.entity;

import com.example.juse.answer.entity.Answer;
import com.example.juse.application.entity.Application;
import com.example.juse.audit.Auditing;
import com.example.juse.board.entity.Board;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.like.entity.Like;
import com.example.juse.question.entity.Question;
import com.example.juse.social.entity.SocialUser;
import com.example.juse.tag.entity.UserTag;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
@Setter
public class User extends Auditing {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String img;

    @Lob
    private String myImg;


    @Column(nullable = false)
    private String introduction;

    @Setter
    @Column(nullable = false)
    private String email;

    private String portfolio;

    private int liked;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "whoLikes", cascade = CascadeType.REMOVE)
    private List<Like> whoLikesList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "whoIsLiked", cascade = CascadeType.REMOVE)
    private List<Like> whoIsLikeList = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<UserTag> userTagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Question> questionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Application> applicationList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "SOCIAL_USER_ID")
    @JsonIgnore
    private SocialUser socialUser;

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

    public boolean isLikedBy(long userId) {
        return this.getWhoIsLikeList().stream().anyMatch(like -> like.getWhoLikes().getId() == userId);
    }

    // USER 테이블의 SOCIAL_USER_ID 컬럼에 Social User Id를 추가한다.

    public void addSocialUser(SocialUser socialUser) {
        this.socialUser = socialUser;
        if (socialUser.getUser() != this) {
            socialUser.setUser(this);
        }
    }

}