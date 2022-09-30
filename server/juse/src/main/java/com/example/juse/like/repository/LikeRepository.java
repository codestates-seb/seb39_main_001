package com.example.juse.like.repository;

import com.example.juse.like.entity.Like;
import com.example.juse.tag.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByWhoLikesIdAndWhoIsLikedId(long whoLikes, long whoIsLiked);

    Optional<Like> findByWhoLikesId(long whoLikes);

}
