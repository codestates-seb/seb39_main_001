package com.example.juse.like.service;

import com.example.juse.helper.stubservice.StubService;
import com.example.juse.like.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"test"})
@RequiredArgsConstructor
@Service
public class LikeServiceStub implements LikeService{

    private final StubService stubService;

    @Override
    public Like create(long whoLikes, long whoIsLiked) {
        return stubService.getLike();
    }

    @Override
    public void delete(long whoLikes, long whoIsLiked) {

    }
}
