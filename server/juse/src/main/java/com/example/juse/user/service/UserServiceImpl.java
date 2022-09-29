package com.example.juse.user.service;

import com.example.juse.tag.entity.BoardTag;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("plain")
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TagService tagService;
    private final UserTagService userTagService;

    private final StorageService storageService;

    private final UserMapper userMapper;

    @Override
    public User getJuse(long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return user;
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
            throw new RuntimeException("자신만 수정할 수 있습니다");
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
        mappedObj.getUserTagList().forEach(
                userTag -> {
                    String tagName = userTag.getTag().getName();
                    Tag tag = tagService.findByName(tagName);
                    userTag.addUser(mappedObj);
                    userTag.addTag(tag);
                }
        );


        return userRepository.save(mappedObj);
    }

    @Override
    public User createUser(User user, MultipartFile profileImg) {

        if(profileImg != null) {
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("images/")
                    .path(profileImg.getOriginalFilename())
                    .toUriString();

            user.setImg(uri);
            storageService.store(profileImg);
        }

        user.getUserTagList().forEach(
                userTag -> {
                    String tagName = userTag.getTag().getName();
                    Tag tag = tagService.findByName(tagName);
                    userTag.addUser(user);
                    userTag.addTag(tag);
                }
        );

        return userRepository.save(user);
    }

    @Override
    public User verifyUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                NoSuchElementException::new
        );
    }

    public User findUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.orElseThrow(NoSuchElementException::new);

        return user;
    }

    @Override
    public boolean isNicknameAvailable(String nickname) {

        return userRepository.findByNickname(nickname).isEmpty();

    }
}
