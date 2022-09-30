package com.example.juse.user.service;

import com.example.juse.exception.CustomRuntimeException;
import com.example.juse.exception.ExceptionCode;
import com.example.juse.tag.entity.Tag;
import com.example.juse.tag.entity.UserTag;
import com.example.juse.tag.service.TagService;
import com.example.juse.tag.service.UserTagService;
import com.example.juse.user.entity.User;
import com.example.juse.user.mapper.UserMapper;
import com.example.juse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TagService tagService;
    private final UserTagService userTagService;
    private final UserMapper userMapper;

    private final StorageService storageService;

    @Override
    public User getJuse(long userId) {
        return verifyUserById(userId);
    }

    @Override
    public User getProfile(long userId) {

        return verifyUserById(userId);
        
    }

    @Override
    public User update(User mappedObj, MultipartFile profileImg) {
        User user = verifyUserById(mappedObj.getId());

        if(profileImg != null) {
            // profile 이미지를 uri 형식으로 전송
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("images/")
                    .path(profileImg.getOriginalFilename())
                    .toUriString();

            user.setImg(uri);
            storageService.store(profileImg);
        }

        long userId = mappedObj.getSocialUser().getId();

        if (user.getSocialUser().getId() != userId) {
            throw new CustomRuntimeException(ExceptionCode.USER_NOT_MATCHED);
        }

        userMapper.updateEntityFromSource(user, mappedObj);

        List<UserTag> list = mappedObj.getUserTagList().stream()
                .map(
                        userTag -> {
                            UserTag userTagEntity =
                                    userTagService.findMappedUserTagOrCreate(userTag, user);

                            return userTagService.create(userTagEntity);
                        }
                ).collect(Collectors.toList());

        List<UserTag> difference =
                user.getUserTagList().stream()
                        .filter(userTag -> !list.contains(userTag))
                        .collect(Collectors.toList());

        user.getUserTagList().removeAll(difference);

        return userRepository.save(user);
    }

    @Override
    public void deleteAccount(long userId) {

        User user = verifyUserById(userId);

        userRepository.deleteById(userId);

    }

    @Override
    public User create(User mappedObj) {

        System.out.println("mappedObj.toString() = " + mappedObj.toString());

        if (!mappedObj.getUserTagList().isEmpty()) {
            mappedObj.getUserTagList().forEach(
                    userTag -> {
                        String tagName = userTag.getTag().getName();
                        Tag tag = tagService.findByName(tagName);
                        userTag.addUser(mappedObj);
                        userTag.addTag(tag);
                    }
            );
        }

        return userRepository.save(mappedObj);
    }

    @Override
    public User createUser(User user, MultipartFile profileImg) {

        String socialUserEmail = user.getSocialUser().getEmail();

        if (isDuplicatedBy(socialUserEmail)) {
            throw new CustomRuntimeException(ExceptionCode.USER_ALREADY_EXISST);
        }

        if(profileImg != null) {
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("images/")
                    .path(profileImg.getOriginalFilename())
                    .toUriString();

            user.setImg(uri);
            storageService.store(profileImg);
        }

        if (!user.getUserTagList().isEmpty()) {
            user.getUserTagList().forEach(
                    userTag -> {
                        String tagName = userTag.getTag().getName();
                        Tag tag = tagService.findByName(tagName);
                        userTag.addUser(user);
                        userTag.addTag(tag);
                    }
            );
        }

        return userRepository.save(user);
    }

    @Override
    public User verifyUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> {
                    throw new CustomRuntimeException(ExceptionCode.USER_NOT_FOUND);
                }
        );
    }

    @Override
    public boolean isNicknameAvailable(String nickname) {

        return userRepository.findByNickname(nickname).isEmpty();

    }

    public boolean isDuplicatedBy(String socialUserEmail) {
        return userRepository.findByEmail(socialUserEmail) != null;
    }
}
