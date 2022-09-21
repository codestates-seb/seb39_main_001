package com.example.juse.board.controller;

import com.example.juse.board.dto.BoardRequestDto;
import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.board.mapper.BoardMapper;
import com.example.juse.board.service.BoardService;
import com.example.juse.dto.MultiResponseDto;
import com.example.juse.dto.Pagination;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.helper.filterings.FilterOptions;
import com.example.juse.security.oauth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boards")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    @PostMapping
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> post(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody BoardRequestDto.Post postDto
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
            @PathVariable("board-id") long boardId
    ) {
        Board foundEntity = boardService.getBoard(boardId);
        BoardResponseDto.Single responseDto = boardMapper.toSingleResponseDto(foundEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }


    @PatchMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> patch(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("board-id") long boardId,
            @RequestBody BoardRequestDto.Patch patchDto
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
            @PathVariable("board-id") long boardId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        boardService.delete(boardId, userId);
        return new ResponseEntity<>(new SingleResponseDto<>("success"), HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<BoardResponseDto.Multi>> getBoards(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "period", required = false) String period,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "page", required = true, defaultValue = "1") int page
    ) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("createdAt").descending());

        FilterOptions filterOptions = FilterOptions.of(type, tag, period, status);
        Page<Board> pagedBoardList = boardService.getBoards(pageable, filterOptions);

        Pagination pagination = Pagination.of(pagedBoardList, filterOptions);

        List<BoardResponseDto.Multi> data = boardMapper.toListDtoFromListEntities(pagedBoardList.getContent());

        return new ResponseEntity<>(new MultiResponseDto<>(data, pagination), HttpStatus.OK);

    }
}
