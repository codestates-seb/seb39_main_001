package com.example.juse.like.service;

import com.example.juse.like.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("real")
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService{


    @Override
    public Like create(long whoLikes, long whoIsLiked) {
        return null;
    }

    @Override
    public void delete(long whoLikes, long whoIsLiked) {

    }
}
