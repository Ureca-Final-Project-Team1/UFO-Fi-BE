package com.example.ufo_fi.domain.user.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NO_USER(HttpStatus.NOT_FOUND, "식별되지 않은 사용자입니다."),
    NO_PHOTO(HttpStatus.INTERNAL_SERVER_ERROR, "사용가능한 프로필 사진이 없습니다."),
    NO_NICKNAME(HttpStatus.INTERNAL_SERVER_ERROR, "사용가능한 닉네임 형용사가 없습니다."),
    RANDOM_NUMBER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "랜덤 값을 생성할 수 없습니다."),
    NO_USER_PLAN(HttpStatus.INTERNAL_SERVER_ERROR, "요금제 등록을 하지 않았습니다."),
    NO_USER_AND_USER_PLAN(HttpStatus.INTERNAL_SERVER_ERROR, "유저 정보가 존재하지 않거나, 요금제 등록을 하지 않았습니다."),
    CANT_UPDATE_USER_PLAN(HttpStatus.INTERNAL_SERVER_ERROR, "이미 판매 내역이 있는 요금제로, 요금제를 업데이트할 수 없습니다."),
    NO_UPDATE_PLAN(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 가능한 요금제가 없습니다."),
    PG_PAYMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "결제 오류가 발생했습니다."),
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
