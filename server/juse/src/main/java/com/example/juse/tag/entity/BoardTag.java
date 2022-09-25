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

    public void addBoard(Board board) {
        this.board = board;
        if (!this.board.getBoardTagList().contains(this)) {
            this.board.getBoardTagList().add(this);
        }
    }

    public void addTag(Tag tag) {
        this.tag = tag;
        if (!this.tag.getBoardTagList().contains(this)) {
            this.tag.getBoardTagList().add(this);
        }
    }

    public static BoardTag of(Board board, Tag tag) {
        BoardTag boardTag = new BoardTag();
        boardTag.addBoard(board);
        boardTag.addTag(tag);
        return boardTag;
    }
}