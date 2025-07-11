package com.example.ufo_fi.domain.signup.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SignupErrorCode implements ErrorCode {
    NO_USER(HttpStatus.NOT_FOUND, "식별되지 않은 사용자입니다."),
    NO_PHOTO(HttpStatus.NOT_FOUND, "사용가능한 프로필 사진이 없습니다."),
    NO_NICKNAME(HttpStatus.NOT_FOUND, "사용가능한 닉네임 형용사가 없습니다.")
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
