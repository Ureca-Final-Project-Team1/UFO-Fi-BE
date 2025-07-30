package com.example.ufo_fi.v2.user.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "식별되지 않은 사용자입니다."),
    RANDOM_NUMBER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "랜덤 값을 생성할 수 없습니다."),
    NO_USER_ACCOUNT(HttpStatus.CONFLICT, "유저의 계정이 등록되어있지 않습니다."),
    ALREADY_ACCOUNT_EXIST(HttpStatus.BAD_REQUEST, "이미 유저의 계정이 존재합니다."),
    ALREADY_USER_SIGNUP(HttpStatus.BAD_REQUEST, "이미 유저가 회원가입 하셨습니다."),
    NOT_DEACTIVE(HttpStatus.BAD_REQUEST, "비활성화된 유저가 아닙니다."),
    INVALID_ROLE(HttpStatus.UNAUTHORIZED, "유저의 ROLE이 잘못되었습니다."),
    NOT_FOUND_NICKNAME(HttpStatus.NOT_FOUND, "사용가능한 닉네임 형용사가 없습니다."),
    NOT_FOUND_PROFILE_PHOTO(HttpStatus.NOT_FOUND, "사용가능한 이미지가 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
