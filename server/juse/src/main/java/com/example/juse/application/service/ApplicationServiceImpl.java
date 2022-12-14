package com.example.juse.application.service;

import com.example.juse.application.entity.Application;
import com.example.juse.application.repository.ApplicationRepository;
import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.board.service.BoardService;
import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("plain")
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @Override
    public Application create(Application mappedObj) {
        long userId = mappedObj.getUser().getId();

        long boardId = mappedObj.getBoard().getId();

        Board board = boardService.verifyBoardById(boardId);

        String position = mappedObj.getPosition();

        checkPositionAvailability(board, position);
        checkDuplicatedByUserIdAndBoardId(userId, boardId);

        Application findApply = findUserIdApplication(userId);
        System.out.println("findApply.toString() = " + findApply.toString());

//        if (findApply.getId() != null && userId == findApply.getUser().getId()) {
//            throw new CustomRuntimeException(ExceptionCode.APPLICATION_DUPLICATED);
//        }

        return applicationRepository.save(mappedObj);
    }

    @Override
    public Application update(Application mappedObj) {
        return null;
    }

    @Override
    @Transactional
    public Application accept(long applicationId, long userId) {
        Application findApply = findVerifiedApplication(applicationId);

        Board board = boardService.verifyBoardById(findApply.getBoard().getId());

        System.out.println("board = " + board.toString());
        System.out.println("findApply = " + findApply.getPosition());

        findApply.checkApplicationWriter(userId);
        checkPositionAvailability(board, findApply.getPosition());
        findApply.setAccepted(true);

        // ????????? ????????? ???, ???????????? ??? ????????? ????????? ?????? ??? Board ???????????? ??????.
        int curBack = board.getCurBackend();
        int curFront = board.getCurFrontend();
        int curDesign = board.getCurDesigner();
        int curEtc = board.getCurEtc();

        switch (findApply.getPosition()) {
            case "backend":
                board.setCurBackend(++curBack);
                break;
            case "frontend":
                board.setCurFrontend(++curFront);
                break;
            case "designer":
                board.setCurDesigner(++curDesign);
                break;
            case "etc":
                board.setCurEtc(++curEtc);
                break;
        }

        System.out.println("board.toString = " + board.toString());

        boardRepository.save(board);

        return applicationRepository.save(findApply);
    }

    @Override
    @Transactional
    public void deny(long applicationId, long userId) {
        Application findApply = findVerifiedApplication(applicationId);
        Board board = boardService.verifyBoardById(findApply.getBoard().getId());
        findApply.checkApplicationWriter(userId);

        // ????????? ????????? ???, ???????????? ??? ????????? ????????? ?????? ??? Board ???????????? ??????.
        int curBack = board.getCurBackend();
        int curFront = board.getCurFrontend();
        int curDesign = board.getCurDesigner();
        int curEtc = board.getCurEtc();

        if (findApply.getPosition().equals("backend") && curBack > 0) board.setCurBackend(--curBack);
        else if(findApply.getPosition().equals("frontend") && curFront > 0) board.setCurFrontend(--curFront);
        else if(findApply.getPosition().equals("designer") && curDesign > 0) board.setCurDesigner(--curDesign);
        else if(findApply.getPosition().equals("etc") && curEtc > 0) board.setCurEtc(--curEtc);

        System.out.println("board.toString = " + board.toString());
        boardRepository.save(board);
        applicationRepository.deleteById(applicationId);

    }

    public Application findVerifiedApplication(long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(
                () -> new CustomRuntimeException(ExceptionCode.APPLICATION_NOT_FOUND)
        );
    }

    public Application findUserIdApplication(long userId) {
        return applicationRepository.findById(userId).orElseGet(
                Application::new
        );
    }

    public void checkDuplicatedByUserIdAndBoardId(long userId, long boardId) {
        if (applicationRepository.findByUserIdAndBoardId(userId, boardId).isPresent()) {
            throw new CustomRuntimeException(ExceptionCode.APPLICATION_DUPLICATED);
        }
    }

    public void checkPositionAvailability(Board board, String position) {
        if (!board.isPositionAvailable(position)) {
            throw new CustomRuntimeException(ExceptionCode.APPLICATION_POSITION_UNAVAILABLE);
        }
    }
}
