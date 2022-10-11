package com.example.juse.board.controller;

import com.example.juse.board.dto.BoardRequestDto;
import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.board.mapper.BoardMapper;
import com.example.juse.board.service.BoardService;
import com.example.juse.dto.MultiResponseDto;
import com.example.juse.dto.Pagination;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.exception.validator.NotEmptyToken;
import com.example.juse.helper.filterings.FilterOptions;
import com.example.juse.security.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Validated
@RequestMapping("/boards")
@RestController
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    @PostMapping
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> post(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @RequestBody @Valid BoardRequestDto.Post postDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        postDto.setUserId(userId);
        Board mappedObj = boardMapper.toEntityFrom(postDto);
        Board createdEntity = boardService.create(mappedObj);
        BoardResponseDto.Single responseDto = boardMapper.toSingleResponseDto(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> getBoard(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("board-id") @Positive long boardId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        Board foundEntity = boardService.getBoard(boardId);

        BoardResponseDto.Single responseDto = boardMapper.toSingleResponseDto(foundEntity);

        try {
            Long userId = principalDetails.getSocialUser().getUser().getId();
            log.info("userId " + userId);

            boardService.addViewCount(foundEntity, request, response, boardId);

            if (userId != null && foundEntity.isCreatedBy(userId)) {
                responseDto.setAuth(true);
                log.info("responseDto.getAuth " + responseDto.isAuth());
            }

            if (foundEntity.isBookmarkedBy(userId)) {
                responseDto.setBookmarked(true);
            }

            if (foundEntity.isWriterLikedBy(userId)) {
                responseDto.setWriterLiked(true);
            }

            responseDto.getQuestionList().stream()
                    .filter(q -> q.getUser().getId() == userId)
                    .forEach(q -> q.setAuth(true));

        } catch (NullPointerException npe) {
            responseDto.setAuth(false);
        }

        boardService.addViewCount(foundEntity, request, response, boardId);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }


    @PatchMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> patch(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("board-id") @Positive long boardId,
            @RequestBody @Valid BoardRequestDto.Patch patchDto
    ) {

        Long userId = principalDetails.getSocialUser().getUser().getId();
        patchDto.setUserId(userId);
        patchDto.setBoardId(boardId);
        Board mappedObj = boardMapper.toEntityFrom(patchDto);
        Board updatedEntity = boardService.update(mappedObj);
        BoardResponseDto.Single responseDto = boardMapper.toSingleResponseDto(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<String>> delete(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("board-id") @Positive long boardId
    ) {

        long userId = principalDetails.getSocialUser().getUser().getId();
        boardService.delete(boardId, userId);
        return new ResponseEntity<>(new SingleResponseDto<>("success"), HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<BoardResponseDto.Multi>> getBoards(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "type", required = false) Board.Type type,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "period", required = false) String period,
            @RequestParam(name = "status", required = false) Board.Status status,
            @RequestParam(name = "page", required = true, defaultValue = "1") int page
    ) {
        System.out.println("type : " + type);
        System.out.println("tag : " + tag);
        System.out.println("period : " + period);
        System.out.println("status : " + status);

        Pageable pageable = PageRequest.of(page - 1, 6);

        FilterOptions filterOptions = FilterOptions.of(type, status, tag, period);
        Page<Board> pagedBoardList = boardService.getBoards(pageable, filterOptions);

        Pagination pagination = Pagination.of(pagedBoardList, filterOptions);

        List<BoardResponseDto.Multi> data = null;

        try {
            long userId = principalDetails.getSocialUser().getUser().getId();

            data = pagedBoardList.getContent().stream().map(board -> {
                BoardResponseDto.Multi multi = boardMapper.toMultiResponseDto(board);

                if (board.isBookmarkedBy(userId)) {
                    multi.setBookmarked(true);
                }

                if (board.isWriterLikedBy(userId)) {
                    multi.setWriterLiked(true);
                }
                return multi;

            }).collect(Collectors.toList());

        } catch (NullPointerException npe) {

            data = boardMapper.toListDtoFromListEntities(pagedBoardList.getContent());

        }

        return new ResponseEntity<>(new MultiResponseDto<>(data, pagination), HttpStatus.OK);

    }
}
