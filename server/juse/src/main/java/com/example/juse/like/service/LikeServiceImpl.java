package com.example.juse.like.service;

import com.example.juse.like.entity.Like;
import com.example.juse.like.repository.LikeRepository;
import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final UserRepository userRepository;

    //todo : user1번이 user2번을 또 누르면 어떻게 할것인가?
    //todo : 하나의 api로 합치는 방법
    //todo :
    @Override
    public Like create(long whoLikes, long whoIsLiked) {
        User whoLike = userRepository.findById(whoLikes).orElseThrow();
        User whoIsLike = userRepository.findById(whoIsLiked).orElseThrow();

        Like like = Like.builder()
                .whoLikes(whoLike)
                .whoIsLiked(whoIsLike)
                .build();

        //
        int liked = whoIsLike.getLiked();
        whoIsLike.setLiked(++liked);

        // todo : if 좋아요가 만약 눌러있을 시 좋아요 취소를 구현하자.

        return likeRepository.save(like);
    }

    @Override
    public void delete(long whoLikes, long whoIsLiked) {
        User whoLike = userRepository.findById(whoLikes).orElseThrow();
        User whoIsLike = userRepository.findById(whoIsLiked).orElseThrow();

        Like like = likeRepository.findByWhoLikesIdAndWhoIsLikedId(whoLikes, whoIsLiked).orElseThrow();
        int liked = whoIsLike.getLiked();

        whoIsLike.setLiked(--liked);

        likeRepository.delete(like);
    }

}
