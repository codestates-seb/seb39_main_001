package com.example.juse.like.service;

import com.example.juse.like.entity.Like;
import com.example.juse.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public Like create(long whoLikes, long whoIsLiked) {
        return null;
    }

    @Override
    public void delete(long whoLikes, long whoIsLiked) {

    }
}
