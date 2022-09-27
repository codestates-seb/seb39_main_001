package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import com.example.juse.board.mapper.BoardMapper;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.helper.filterings.FilterOptions;
import com.example.juse.tag.entity.BoardTag;
import com.example.juse.tag.entity.Tag;
import com.example.juse.tag.repository.BoardTagRepository;
import com.example.juse.tag.service.BoardTagService;
import com.example.juse.tag.service.TagService;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final TagService tagService;
    private final BoardMapper boardMapper;
    private final BoardTagRepository boardTagRepository;
    private final BoardTagService boardTagService;

    @Override
    @Transactional
    public Board create(Board post) {

        User foundUser = userService.verifyUserById(post.getUser().getId());

        if (post.getBoardTagList().isEmpty()) {
            throw new CustomRuntimeException(ExceptionCode.BOARD_WITHOUT_TAG);
        }

        post.getBoardTagList().forEach(
            boardTag -> {
                String tagName = boardTag.getTag().getName();
                Tag tag = tagService.findByName(tagName);
                boardTag.addBoard(post);
                boardTag.addTag(tag);
            }
        );

        post.addUser(foundUser);

        return boardRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Board getBoard(long boardId) {

        Board entity = verifyBoardById(boardId);
        return entity;
    }

    @Override
    @Transactional
    public Board update(Board patch) {

        Board board = verifyBoardById(patch.getId());
        long userId = patch.getUser().getId();

        if (!board.isCreatedBy(userId)) {
            throw new CustomRuntimeException(ExceptionCode.BOARD_WRITER_NOT_MATCHED);
        }

        if (patch.getBoardTagList().isEmpty()) {
            throw new CustomRuntimeException(ExceptionCode.BOARD_WITHOUT_TAG);
        }

        boardMapper.updateEntityFromSource(board, patch);

        List<BoardTag> list = patch.getBoardTagList().stream()
                .map(
                        boardTag -> {
                            BoardTag boardTagEntity =
                                    boardTagService.findMappedBoardTagOrCreate(boardTag, board);

                            return boardTagService.create(boardTagEntity);
                        }
                ).collect(Collectors.toList());

        List<BoardTag> difference =
                board.getBoardTagList().stream()
                        .filter(boardTag -> !list.contains(boardTag))
                        .collect(Collectors.toList());

        board.getBoardTagList().removeAll(difference);

        return boardRepository.save(board);

    }

    @Override
    @Transactional
    public void delete(long boardId, long userId) {

        Board board = verifyBoardById(boardId);

        if (!board.isCreatedBy(userId)) {
            throw new CustomRuntimeException(ExceptionCode.BOARD_WRITER_NOT_MATCHED);
        }

        boardRepository.delete(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Board> getBoards(Pageable pageable, FilterOptions filterOptions) {

        return

//                boardRepository.findAll(pageable);

                boardRepository
                .findWithParameters(
                        pageable,
                        filterOptions.getStatus(),
                        filterOptions.getType(),
                        filterOptions.getTag(),
                        filterOptions.getPeriod());
    }

    @Override
    public Board verifyBoardById(long boardId) {
        return boardRepository
                .findById(boardId)
                .orElseThrow(
                        () -> new CustomRuntimeException(ExceptionCode.BOARD_NOT_FOUND)
                );
    }
}


