package com.example.juse.tag.entity;

import com.example.juse.board.entity.Board;
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
@Table(name = "BOARDS_TAGS")
public class BoardTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;
}