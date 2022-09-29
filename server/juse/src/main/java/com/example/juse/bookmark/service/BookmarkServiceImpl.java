package com.example.juse.bookmark.service;

import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.board.service.BoardService;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.bookmark.repository.BookmarkRepository;
import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.user.entity.User;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Override
    @Transactional
    public Bookmark create(long boardId, long userId) {
        User user = userService.verifyUserById(userId);
        System.out.println("############user.getId() = " + user.getId());
        Board board = boardService.verifyBoardById(boardId);
        if (board.isBookmarkedBy(userId)) {
            delete(boardId, userId);
        }
//        Bookmark findBookmark = findUserIdBookmark(userId);
//        System.out.println("############findBookmark.getId() = " + findBookmark.getId());
//        System.out.println("userId = " + userId);
//        if (findBookmark.getId() != null && userId == findBookmark.getUser().getId()) {
//            delete(boardId, userId);
//            return null;
//
//        }
        else {
            Bookmark bookmark = Bookmark.builder()
                    .user(user)
                    .board(board)
                    .build();

            // Board 테이블에 bookmark 카운트 추가
            int bookCount = board.getBookmarks();
            board.setBookmarks(++bookCount);
            boardRepository.save(board);
            return bookmarkRepository.save(bookmark);
        }

        return null;
    }

    @Override
    @Transactional
    public void delete(long boardId, long userId) {
        Board board = boardService.verifyBoardById(boardId);

        Bookmark bookmark = findByBoardIdAndUserId(boardId, userId);

        // Board 테이블에 bookmark 카운트 감소
        int bookCount = board.getBookmarks();
        board.setBookmarks(--bookCount);
        board.getBookmarkList().remove(bookmark);
        boardRepository.save(board);

//        bookmarkRepository.deleteById(bookmark.getId());
    }

    public Bookmark findUserIdBookmark(long userId) {
        return bookmarkRepository.findByUserId(userId).orElseGet(
                Bookmark::new
        );
    }

    public Bookmark findByBoardIdAndUserId(long boardId, long userId) {
        return bookmarkRepository.findByBoardIdAndUserId(boardId, userId).orElseThrow(
                () -> new CustomRuntimeException(ExceptionCode.BOOKMARK_NOT_FOUND)
        );
    }
}
