package com.example.juse.bookmark.service;

import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.bookmark.entity.Bookmark;
import com.example.juse.bookmark.repository.BookmarkRepository;
import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public Bookmark create(long boardId, long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        System.out.println("############user.getId() = " + user.getId());
        Board board = boardRepository.findById(boardId).orElseThrow();

        Bookmark findBookmark = findBoardAndUserIdBookmark(boardId, userId);
        System.out.println("############findBookmark.getId() = " + findBookmark.getId());
        System.out.println("userId = " + userId);
        if (findBookmark.getId() != null && userId == findBookmark.getUser().getId() && boardId == findBookmark.getBoard().getId()) {
            delete(boardId, userId);
            return null;

        }
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
    }

    @Override
    public void delete(long boardId, long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        Bookmark bookmark = bookmarkRepository.findByBoardIdAndUserId(boardId, userId).orElseThrow();

        // Board 테이블에 bookmark 카운트 감소
        int bookCount = board.getBookmarks();
        board.setBookmarks(--bookCount);
        boardRepository.save(board);

        bookmarkRepository.deleteById(bookmark.getId());
    }

    public Bookmark findBoardAndUserIdBookmark(long boardId, long userId) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByBoardIdAndUserId(boardId, userId);

        Bookmark bookmark = optionalBookmark.orElseGet(() -> new Bookmark());

        return bookmark;
    }
}
