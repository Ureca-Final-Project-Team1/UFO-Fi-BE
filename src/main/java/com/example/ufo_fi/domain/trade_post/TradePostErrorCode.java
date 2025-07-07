package com.example.ufo_fi.domain.trade_post;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TradePostErrorCode implements ErrorCode {
    NEED_USER_ONBOARDING(HttpStatus.FORBIDDEN, "온보딩이 필요한 사용자입니다.");


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
