package com.example.juse.tag.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TAGS")
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    //    @Column(nullable = false)
    private Type type;

    @Getter
    public enum Type {

        BACKEND,
        FRONTEND,
        MOBILE,
        ETC

    }

    @Builder.Default
    @OneToMany(mappedBy = "tag")
    private List<BoardTag> boardTagList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "tag")
    private List<UserTag> userTagList = new ArrayList<>();
}