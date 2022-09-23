package com.example.juse.tag.service;

import com.example.juse.tag.entity.Tag;
import com.example.juse.tag.entity.UserTag;
import com.example.juse.tag.repository.UserTagRepository;
import com.example.juse.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserTagService {

    private final UserTagRepository userTagRepository;
    private final TagService tagService;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserTag create(UserTag userTag) {

        return userTagRepository.save(userTag);
    }

    public UserTag findMappedUserTagOrCreate(UserTag userTag, User user) {

        String tagName = userTag.getTag().getName();

        return userTagRepository
                .findByTagNameAndUserId(tagName, user.getId())
                .orElseGet(
                        () -> {
                            Tag tag = tagService.findByName(tagName);
                            return UserTag.of(user, tag);
                        }
                );
    }
}
