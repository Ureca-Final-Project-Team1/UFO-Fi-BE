package com.example.ufo_fi.domain.user.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserAccountErrorCode implements ErrorCode {
    NO_USER_ACCOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "유저의 계정이 등록되어있지 않습니다."),
    ALREADY_ACCOUNT_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "이미 유저의 계정이 존재합니다.");

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