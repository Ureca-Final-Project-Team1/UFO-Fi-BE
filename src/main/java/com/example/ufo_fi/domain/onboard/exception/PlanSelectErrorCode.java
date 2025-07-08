package com.example.ufo_fi.domain.onboard.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlanSelectErrorCode implements ErrorCode {
    OVER_SELLABLE(HttpStatus.BAD_REQUEST, "실제 팔 수 있는 모바일 데이터는 무제한 20GB/월 정액 10%입니다."),
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