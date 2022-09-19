package com.example.juse.board.controller;

import com.example.juse.board.dto.BoardRequestDto;
import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.board.entity.Board;
import com.example.juse.board.mapper.BoardMapper;
import com.example.juse.board.service.BoardSerivice;
import com.example.juse.dto.MultiResponseDto;
import com.example.juse.dto.Pagination;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.helper.filterings.FilterOptions;
import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
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

    private final BoardSerivice boardSerivice;
    private final BoardMapper boardMapper;

    private final UserRepository userRepository;

    //todo : user 정보를 받아올 수 있는 메서드를 서비스 레이어에 만들어 두고 공통으로 쓰면 될 것 같다.
    //todo : principal에서 유저 정보 추출 (이메일 또는 아이디) -> 서비스에서 조회 후 예외처리

    @PostMapping
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> post(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody BoardRequestDto.Post postDto
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        postDto.setUserId(userId);
        Board mappedObj = boardMapper.toEntityFrom(postDto);
        Board createdEntity = boardSerivice.create(mappedObj);
        BoardResponseDto.Single responseDto = boardMapper.toSingleResponseDto(createdEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<BoardResponseDto.Single>> getBoard(
            @PathVariable("board-id") long boardId
    ) {
        Board foundEntity = boardSerivice.getBoard(boardId);
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
        Board updatedEntity = boardSerivice.update(mappedObj);
        BoardResponseDto.Single responseDto = boardMapper.toSingleResponseDto(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity<SingleResponseDto<String>> delete(
            @PathVariable("board-id") long boardId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        boardSerivice.delete(boardId, userId);
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

        Page<Board> pagedBoardList = boardSerivice.getBoards(pageable);
        FilterOptions filterOptions = FilterOptions.of(type, tag, period, status);
        Pagination pagination = Pagination.of(pagedBoardList, filterOptions);

        List<BoardResponseDto.Multi> data = boardMapper.toListDtoFromListEntities(pagedBoardList.getContent());

        return new ResponseEntity<>(new MultiResponseDto<>(data, pagination), HttpStatus.OK);

    }
}
