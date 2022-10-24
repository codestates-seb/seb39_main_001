package com.example.juse.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    AUTH_TOKEN_EXPIRED(-600, "토큰의 유효기간이 끝났습니다."),
    AUTH_TOKEN_INVALID(-601, "유효한 토큰이 아닙니다"),
    AUTH_TOKEN_NULL(-603, "토큰 정보가 없습니다"),

    USER_NOT_FOUND(-700, "사용자 정보가 없습니다"),
    USER_NOT_AUTHENTICATED(-701, "인증된 사용자가 아닙니다. 소셜 로그인을 통해 인증을 진행해주세요"),
    SOCIAL_USER_NOT_FOUND(-702, "소셜 로그인 이메일 정보가 없습니다"),
    USER_NOT_MATCHED(-703, "사용자 본인만 수정 또는 삭제할 수 있습니다"),
    USER_ALREADY_EXIST(-704, "이미 존재하는 회원입니다"),

    NOT_VALID_IMAGE_TYPE(-705, "이미지 파일만 업로드 가능합니다."),

    BOARD_WITHOUT_TAG(-800, "설정된 기술 스택 태그가 없습니다. 스택을 설정하세요"),
    BOARD_NOT_FOUND(-801, "없는 게시글입니다"),
    BOARD_WRITER_NOT_MATCHED(-802, "게시글 작성자만 수정할 수 있습니다"),
    BOARD_INVALID_FILTERING_OPTIONS(-803, "필터링 옵션이 유효하지 않습니다"),

    QUESTION_NOT_FOUND(-900, "질문을 찾을 수 없습니다"),
    QUESTION_WRITER_NOT_MATCHED(-901, "질문 작성자만 수정할 수 있습니다"),

    ANSWER_NOT_FOUND(-1000, "답변을 찾을 수 없습니다"),
    ANSWER_BOARD_WRITER_NOT_MATCHED(-1001, "게시글 작성자만 질문에 답변을 남길 수 있습니다"),
    ANSWER_WRITER_NOT_MATCHED(-1002, "답변 작성자만 수정 또는 삭제할 수 있습니다"),
    QUESTION_ALREADY_ANSWERED(-1003, "이미 답변된 문의입니다."),

    BOOKMARK_NOT_FOUND(-1100, "북마크 정보가 없습니다"),
    BOOKMARK_DUPLICATED(-1101, "이미 저장된 북마크 정보가 있습니다"),

    APPLICATION_NOT_FOUND(-1200, "지원 정보가 없습니다"),
    APPLICATION_DUPLICATED(-1201, "중복 지원할 수 없습니다"),
    APPLICATION_INVALID_REQUEST(-1202, "모집자만 지원 정보를 수정 또는 삭제 할 수 있습니다"),
    APPLICATION_POSITION_UNAVAILABLE(-1203, "지원하려는 포지션의 모집 정원이 찼습니다"),
    SELF_APPLICATION_NOT_ALLOWED(-1204, "자신이 작성한 글에 지원할 수 없습니다"),

    LIKE_NOT_FOUND(-1300, "좋아요한 정보를 찾을 수 없습니다"),
    LIKE_DUPLICATED(-1301, "이미 좋아요를 눌렀습니다"),

    TAG_NOT_FOUND(-1401, "요청한 태그 정보가 없습니다"),

    INVALID_METHOD_ARGUMENT(-10000, "유효하지 않은 인자가 입력되었습니다. 파라미터를 다시 확인해주세요"),
    UNKNOWN_ERROR(-10001, "알 수 없는 문제가 발생했습니다"),
    NOTIFICATION_NOT_FOUND(-10002, "잘못된 알림 정보입니다");


    final int code;
    final String desc;

}
