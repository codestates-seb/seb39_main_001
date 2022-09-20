package com.example.juse.board.service;

import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.tag.entity.Tag;
import com.example.juse.tag.service.TagService;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardSerivice{

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final TagService tagService;

    @Override
    public Board create(Board post) {

        User foundUser = userService.verifyUserById(post.getUser().getId());

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
    public Board getBoard(long boardId) {
        return null;
    }

    @Override
    public Board update(Board patch) {
        return null;
    }

    @Override
    public void delete(long boardId, long userId) {

    }

    @Override
    public Page<Board> getBoards(Pageable pageable) {
        return null;
    }


}


