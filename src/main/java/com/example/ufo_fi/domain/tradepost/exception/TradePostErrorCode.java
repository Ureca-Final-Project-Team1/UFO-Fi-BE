package com.example.ufo_fi.domain.tradepost.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TradePostErrorCode implements ErrorCode {

    NEED_USER_ONBOARDING(HttpStatus.FORBIDDEN, "온보딩이 필요한 사용자입니다."),
    TRADE_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
    ALREADY_DELETE(HttpStatus.GONE, "이미 삭제된 게시글입니다."),
    EXCEED_SELL_CAPACITY(HttpStatus.GONE, "판매 가능 용량을 초과했습니다."),
    EXCEED_RESTORE_CAPACITY(HttpStatus.GONE, "회수량과 잔여 데이터량이 판매 가능 용량보다 많습니다."),
    ACCOUNT_NOT_REGISTERED(HttpStatus.FORBIDDEN, "계좌를 입력해야 됩니다."),
    CANNOT_DELETE_NOT_SELLING_POST(HttpStatus.FORBIDDEN, "판매중 게시물만 삭제 가능합니다."),
    NO_RECOMMENDATION_FOUND(HttpStatus.NOT_FOUND, "조건에 맞는 추천 상품을 찾을 수 없습니다."),
    NO_AUTHORITY(HttpStatus.NOT_ACCEPTABLE, "게시물에 대한 수정 및 삭제 권한이 없습니다.");

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
