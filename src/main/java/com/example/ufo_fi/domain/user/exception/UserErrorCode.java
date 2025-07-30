package com.example.ufo_fi.domain.user.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NO_USER(HttpStatus.NOT_FOUND, "식별되지 않은 사용자입니다."),
    NO_PHOTO(HttpStatus.NOT_FOUND, "사용가능한 프로필 사진이 없습니다."),
    NO_NICKNAME(HttpStatus.NOT_FOUND, "사용가능한 닉네임 형용사가 없습니다."),
    RANDOM_NUMBER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "랜덤 값을 생성할 수 없습니다."),
    NO_USER_PLAN(HttpStatus.NOT_FOUND, "회원님의 요금제가 없습니다."),
    CANT_UPDATE_USER_PLAN(HttpStatus.BAD_REQUEST, "이미 판매 내역이 있는 요금제로, 요금제를 업데이트할 수 없습니다."),
    NO_UPDATE_PLAN(HttpStatus.NOT_FOUND, "업데이트 가능한 요금제가 없습니다."),
    NO_USER_ACCOUNT(HttpStatus.CONFLICT, "유저의 계정이 등록되어있지 않습니다."),
    ALREADY_ACCOUNT_EXIST(HttpStatus.BAD_REQUEST, "이미 유저의 계정이 존재합니다."),
    NO_PLAN(HttpStatus.NOT_FOUND, "모바일 요금제 정보가 없습니다."),
    ALREADY_USER_SIGNUP(HttpStatus.BAD_REQUEST, "이미 유저가 회원가입 하셨습니다."),
    NOT_DEACTIVE(HttpStatus.BAD_REQUEST, "비활성화된 유저가 아닙니다.");

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
