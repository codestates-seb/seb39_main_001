package com.example.juse.like.service;

import com.example.juse.like.entity.Like;

public interface LikeService {

    Like create(long whoLikes, long whoIsLiked);

    void delete(long whoLikes, long whoIsLiked);
}
