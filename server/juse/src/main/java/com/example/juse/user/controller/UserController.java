package com.example.juse.user.controller;

import com.example.juse.board.dto.BoardResponseDto;
import com.example.juse.dto.MultiResponseDto;
import com.example.juse.dto.Pagination;
import com.example.juse.dto.SingleResponseDto;
import com.example.juse.exception.validator.NotEmptyToken;
import com.example.juse.notification.dto.NotificationResponseDto;
import com.example.juse.notification.entity.Notification;
import com.example.juse.notification.mapper.NotificationMapper;
import com.example.juse.notification.service.NotificationService;
import com.example.juse.security.oauth.PrincipalDetails;
import com.example.juse.social.entity.SocialUser;
import com.example.juse.user.dto.UserRequestDto;
import com.example.juse.user.dto.UserResponseDto;
import com.example.juse.user.entity.User;
import com.example.juse.user.mapper.UserMapper;
import com.example.juse.user.repository.UserRepository;
import com.example.juse.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @GetMapping("/join")
    public ResponseEntity joinUser() {
        return new ResponseEntity<>("noUser", HttpStatus.OK);
    }

    @PostMapping(value = "/join", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity userJoin(@AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
                                   @RequestPart @Valid UserRequestDto.Post userPostDto,
                                   @RequestPart(required = false) MultipartFile profileImg) {

        User mappedObj = userMapper.toEntityFrom(userPostDto);
        SocialUser socialUser = principalDetails.getSocialUser();
        mappedObj.setEmail(principalDetails.getSocialUser().getEmail());
        mappedObj.addSocialUser(socialUser);

        User createdUser = userService.createUser(mappedObj, profileImg);
        UserResponseDto.MyProfile response = userMapper.toMyProfileDtoFrom(createdUser);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);

    }

    @GetMapping("/myjuse")
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyJuse>> getMyjuse(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        System.out.println("userId = " + principalDetails.getSocialUser().getUser().getId());

        User foundUser = userService.getJuse(userId);
        UserResponseDto.MyJuse responseDto = userMapper.toMyJuseDtoFrom(foundUser);

        responseDto.getMyBookmarkList().forEach(
                dto -> dto.setBookmarked(true)
        );

        userMapper.updateDtoFromEntity(responseDto.getMyApplicationList(), foundUser);
        userMapper.updateDtoFromEntity(responseDto.getMyParticipationList(), foundUser);
        userMapper.updateDtoFromEntity(responseDto.getMyBoards(), foundUser);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyProfile>> getProfile(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails

    ) {

        long userId = principalDetails.getSocialUser().getUser().getId();

        User userProfile = userService.getProfile(userId);
        UserResponseDto.MyProfile responseDto = userMapper.toMyProfileDtoFrom(userProfile);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{other-user-id}")
    public ResponseEntity<SingleResponseDto<UserResponseDto.Profile>> getOtherUserProfile(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @PathVariable("other-user-id") @Positive long userId
    ) {
        long myId = principalDetails.getSocialUser().getUser().getId();
        User userProfile = userService.getProfile(userId);
        UserResponseDto.Profile responseDto = userMapper.toProfileDtoFrom(userProfile);

        if (userProfile.isLikedBy(myId)) {
            responseDto.setLikedByMe(true);
        }

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<com.example.juse.dto.SingleResponseDto<UserResponseDto.MyProfile>> patch(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails,
            @RequestPart @Valid UserRequestDto.Patch patchDto,
            @RequestPart(required = false) MultipartFile profileImg
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        SocialUser socialUser = principalDetails.getSocialUser();
        patchDto.setId(userId);
        patchDto.setEmail(principalDetails.getSocialUser().getEmail());

        User mappedObj = userMapper.toEntityFrom(patchDto);
        mappedObj.addSocialUser(socialUser);
        User updatedEntity = userService.update(mappedObj, profileImg);
        UserResponseDto.MyProfile responseDto = userMapper.toMyProfileDtoFrom(updatedEntity);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteAccount(
            @AuthenticationPrincipal @NotEmptyToken PrincipalDetails principalDetails
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();

        userService.deleteAccount(userId);
    }

    @GetMapping("/nicknames")
    public ResponseEntity<SingleResponseDto<Boolean>> findNicknames(
            @RequestParam("q") String nickname
    ) {
        Boolean response = userService.isNicknameAvailable(nickname);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public ResponseEntity<SingleResponseDto<? extends List<NotificationResponseDto>>> getNotificationsOnMain(
            @NotEmptyToken @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        List<Notification> notificationList = notificationService.getUnreadNotifications(principalDetails.getSocialUser().getUser().getId());
        List<NotificationResponseDto> response = notificationMapper.mapToNotificationResponseDtoList(notificationList);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/notifications/inbox")
    public ResponseEntity<MultiResponseDto<NotificationResponseDto>> getNotifications(
            @NotEmptyToken @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "read", required = false) Boolean isRead,
            @RequestParam(name = "page", required = true, defaultValue = "1") int page
    ) {

        long receiverId = principalDetails.getSocialUser().getUser().getId();
        Pageable pageable = PageRequest.of(page - 1, 8);

        Page<Notification> pagedNotifications = notificationService.getAllNotifications(pageable, receiverId, isRead);

        List<Notification> notificationList = pagedNotifications.getContent();
        List<NotificationResponseDto> notificationResponseDtoList = notificationMapper.mapToNotificationResponseDtoList(notificationList);
        Pagination pagination = Pagination.of(pagedNotifications);

        return new ResponseEntity<>(new MultiResponseDto<>(notificationResponseDtoList, pagination), HttpStatus.OK);

    }

    @PatchMapping("/notifications")
    public ResponseEntity<SingleResponseDto<String>> setAsRead(
            @NotEmptyToken @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        long userId = principalDetails.getSocialUser().getUser().getId();
        notificationService.setAllNotificationsAsRead(userId);

        return new ResponseEntity<>(new SingleResponseDto<>(("set all notifications as read")), HttpStatus.OK);
    }
}
