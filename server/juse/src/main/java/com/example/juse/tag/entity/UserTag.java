package com.example.juse.tag.entity;

import com.example.juse.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS_TAGS")
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;


    public void addUser(User user) {
        this.user = user;
        if (!this.user.getUserTagList().contains(this)) {
            this.user.getUserTagList().add(this);
        }
    }

    public void addTag(Tag tag) {
        this.tag = tag;
        if (!this.tag.getUserTagList().contains(this)) {
            this.tag.getUserTagList().add(this);
        }
    }

    public static UserTag of(User user, Tag tag) {
        UserTag userTag = new UserTag();
        userTag.addUser(user);
        userTag.addTag(tag);

        return userTag;
    }
}