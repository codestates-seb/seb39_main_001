package com.example.juse.application.service;

import com.example.juse.application.entity.Application;
import com.example.juse.application.repository.ApplicationRepository;
import com.example.juse.board.entity.Board;
import com.example.juse.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Profile("real")
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final BoardRepository boardRepository;

    @Override
    public Application create(Application mappedObj) {
        return applicationRepository.save(mappedObj);
    }

    @Override
    public Application update(Application mappedObj) {
        return null;
    }

    @Override
    public Application accept(long applicationId, long userId) {
        Application findApply = findVerifiedApplication(applicationId);

        Board board = boardRepository.findById(findApply.getBoard().getId()).orElseThrow();

        System.out.println("board = " + board.toString());
        System.out.println("findApply = " + findApply.getPosition());

        findApply.checkApplicationWriter(userId);
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

        System.out.println("board.toString = " + board.toString());

        boardRepository.save(board);

        return applicationRepository.save(findApply);
    }

    @Override
    public void deny(long applicationId, long userId) {
        Application findApply = findVerifiedApplication(applicationId);
        Board board = boardRepository.findById(findApply.getBoard().getId()).orElseThrow();
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
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);

        Application application = optionalApplication.orElseThrow(NoSuchElementException::new);

        return application;

    }
}
