package com.example.juse.like.service;

import com.example.juse.like.entity.Like;
import com.example.juse.like.repository.LikeRepository;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.service.NotificationService;
import com.example.juse.user.entity.User;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    //todo : user1번이 user2번을 또 누르면 어떻게 할것인가?
    //todo : 하나의 api로 합치는 방법
    //todo :
    @Override
    @Transactional
    public Like create(long whoLikes, long whoIsLiked) {
        User whoLike = userRepository.findById(whoLikes).orElseThrow();
        User whoIsLike = userRepository.findById(whoIsLiked).orElseThrow();

        Like findLike = findWhoLikesIdAndWhoIsLikedId(whoLikes, whoIsLiked);

        if (findLike.getId() != null && whoLikes == findLike.getWhoLikes().getId() && whoIsLiked == findLike.getWhoIsLiked().getId()) {
            delete(whoLikes, whoIsLiked);
            return null;
        } else {

            Like like = Like.builder()
                    .whoLikes(whoLike)
                    .whoIsLiked(whoIsLike)
                    .build();

            //
            int liked = whoIsLike.getLiked();
            whoIsLike.setLiked(++liked);


            Like savedLike = likeRepository.save(like);
            notificationService.notifySomeoneLikedUser(like);
            return savedLike;
        }
    }

    @Override
    public void delete(long whoLikes, long whoIsLiked) {
        User whoLike = userRepository.findById(whoLikes).orElseThrow();
        User whoIsLike = userRepository.findById(whoIsLiked).orElseThrow();

        Like like = likeRepository.findByWhoLikesIdAndWhoIsLikedId(whoLikes, whoIsLiked).orElseThrow();
        int liked = whoIsLike.getLiked();

        whoIsLike.setLiked(--liked);
        Notification notification = verifyAndretutruneEntiy;

        likeRepository.delete(like);
    }

    public Like findWhoLikesIdAndWhoIsLikedId(long whoLikes, long whoIsLiked) {
        Optional<Like> optionalLike = likeRepository.findByWhoLikesIdAndWhoIsLikedId(whoLikes, whoIsLiked);

        Like like = optionalLike.orElseGet(() -> new Like());

        return like;
    }
}
