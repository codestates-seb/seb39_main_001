package com.example.juse.tag.service;

import com.example.juse.board.entity.Board;
import com.example.juse.tag.entity.BoardTag;
import com.example.juse.tag.entity.Tag;
import com.example.juse.tag.repository.BoardTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardTagService {

    private final BoardTagRepository boardTagRepository;
    private final TagService tagService;

    @Transactional(propagation = Propagation.REQUIRED)
    public BoardTag create(BoardTag boardTag) {

        return boardTagRepository.save(boardTag);
    }

    public BoardTag findMappedBoardTagOrCreate(BoardTag boardTag, Board board) {

        String tagName = boardTag.getTag().getName();

        return boardTagRepository
                .findByTagNameAndBoardId(tagName, board.getId())
                .orElseGet(
                        () -> {
                            Tag tag = tagService.findByName(tagName);
                            return BoardTag.of(board, tag);
                        }
                );
    }
}
