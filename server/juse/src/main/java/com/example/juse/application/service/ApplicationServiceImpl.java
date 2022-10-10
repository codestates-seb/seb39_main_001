package com.example.juse.application.service;

import com.example.juse.application.entity.Application;
import com.example.juse.application.repository.ApplicationRepository;
import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import com.example.juse.board.service.BoardService;
import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.notification.service.NotificationService;
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
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Application create(Application mappedObj) {
        long userId = mappedObj.getUser().getId();
        long boardId = mappedObj.getBoard().getId();
        Board board = boardService.verifyBoardById(boardId);
        String position = mappedObj.getPosition();

        checkPositionAvailability(board, position);
        checkDuplicatedByUserIdAndBoardId(userId, boardId);
        checkSelfApplication(board, userId);

        Application createdApplication = applicationRepository.save(mappedObj);
        notificationService.notifyNewApplication(board, createdApplication);

        return createdApplication;
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

        findApply.checkApplicationWriter(userId);
        checkPositionAvailability(board, findApply.getPosition());
        findApply.setAccepted(true);

        // 수락을 눌렀을 때, 지원자의 각 포지션 카운트 증가 후 Board 테이블에 저장.
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

        boardRepository.save(board);

        Application createdApplication = applicationRepository.save(findApply);
        notificationService.notifyApplicationAccepted(board, createdApplication);

        return createdApplication;
    }

    @Override
    @Transactional
    public void decline(long applicationId, long userId) {
        Application findApply = findVerifiedApplication(applicationId);
        Board board = boardService.verifyBoardById(findApply.getBoard().getId());
        findApply.checkApplicationWriter(userId);

        // 거절을 눌렀을 때, 지원자의 각 포지션 카운트 감소 후 Board 테이블에 저장.
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

    private void checkDuplicatedByUserIdAndBoardId(long userId, long boardId) {
        if (applicationRepository.findByUserIdAndBoardId(userId, boardId).isPresent()) {
            throw new CustomRuntimeException(ExceptionCode.APPLICATION_DUPLICATED);
        }
    }

    private void checkPositionAvailability(Board board, String position) {
        if (!board.isPositionAvailable(position)) {
            throw new CustomRuntimeException(ExceptionCode.APPLICATION_POSITION_UNAVAILABLE);
        }
    }

    private void checkSelfApplication(Board board, long userId) {
        if (board.isCreatedBy(userId)) {
            throw new CustomRuntimeException(ExceptionCode.SELF_APPLICATION_NOT_ALLOWED);
        }
    }

}
