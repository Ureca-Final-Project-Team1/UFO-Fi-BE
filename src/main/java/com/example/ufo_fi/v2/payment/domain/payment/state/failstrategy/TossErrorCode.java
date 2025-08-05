package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TossErrorCode implements ErrorCode {
    INVALID_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 요청입니다.", TossErrorStrategy.RETRIABLE),
    CARD_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카드사 오류", TossErrorStrategy.RETRIABLE),
    UNKNOWN_PAYMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류", TossErrorStrategy.RETRIABLE),
    PROVIDER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "결제 시스템 오류", TossErrorStrategy.RETRIABLE),
    FAILED_INTERNAL_SYSTEM_PROCESSING(HttpStatus.INTERNAL_SERVER_ERROR, "토스 서버 내부 오류", TossErrorStrategy.RETRIABLE),
    FAILED_PAYMENT_INTERNAL_SYSTEM_PROCESSING(HttpStatus.INTERNAL_SERVER_ERROR, "결제 처리 중 시스템 오류", TossErrorStrategy.RETRIABLE),
    INVALID_API_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 API 키입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    INVALID_CARD_NUMBER(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 카드 번호입니다.", TossErrorStrategy.USER_FIXABLE),
    UNAUTHORIZED_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "인증되지 않은 키입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    INVALID_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "결제 비밀번호 오류입니다.", TossErrorStrategy.USER_FIXABLE),
    INCORRECT_BASIC_AUTH_FORMAT(HttpStatus.INTERNAL_SERVER_ERROR, "Basic 인증 포맷 오류", TossErrorStrategy.IMMEDIATE_FAIL),
    NOT_FOUND_PAYMENT(HttpStatus.INTERNAL_SERVER_ERROR, "존재하지 않는 결제 정보입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    NOT_FOUND_PAYMENT_SESSION(HttpStatus.INTERNAL_SERVER_ERROR, "결제 세션이 없습니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    INVALID_STOPPED_CARD(HttpStatus.INTERNAL_SERVER_ERROR, "정지된 카드입니다.", TossErrorStrategy.USER_FIXABLE),
    INVALID_CARD_EXPIRATION(HttpStatus.INTERNAL_SERVER_ERROR, "카드 유효기간 오류", TossErrorStrategy.USER_FIXABLE),
    INVALID_CARD_LOST_OR_STOLEN(HttpStatus.INTERNAL_SERVER_ERROR, "분실/도난 카드", TossErrorStrategy.USER_FIXABLE),
    REJECT_CARD_PAYMENT(HttpStatus.INTERNAL_SERVER_ERROR, "한도초과 또는 잔액부족", TossErrorStrategy.USER_FIXABLE),
    REJECT_CARD_COMPANY(HttpStatus.INTERNAL_SERVER_ERROR, "카드사 승인 거절", TossErrorStrategy.USER_FIXABLE),
    FDS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "위험 거래 감지", TossErrorStrategy.REPORTED_USER),
    EXCEED_MAX_CARD_INSTALLMENT_PLAN(HttpStatus.INTERNAL_SERVER_ERROR, "할부 개월 수 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    INVALID_CARD_INSTALLMENT_PLAN(HttpStatus.INTERNAL_SERVER_ERROR, "할부 개월 수 오류", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "금액 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_DAILY_PAYMENT_COUNT(HttpStatus.INTERNAL_SERVER_ERROR, "일일 결제 횟수 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_PAYMENT_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "결제 금액 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_ONE_DAY_WITHDRAW_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "1일 출금 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_ONE_TIME_WITHDRAW_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "1회 출금 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_MONTHLY_PAYMENT_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "월간 결제 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_AUTH_COUNT(HttpStatus.INTERNAL_SERVER_ERROR, "인증 시도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_ONE_DAY_AMOUNT(HttpStatus.INTERNAL_SERVER_ERROR, "일일 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    NOT_REGISTERED_BUSINESS(HttpStatus.INTERNAL_SERVER_ERROR, "등록되지 않은 사업자번호", TossErrorStrategy.SERVER_CONFIG_ERROR),
    INVALID_AUTHORIZE_AUTH(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 인증 방식", TossErrorStrategy.SERVER_CONFIG_ERROR),
    FORBIDDEN_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "허용되지 않은 요청", TossErrorStrategy.SERVER_CONFIG_ERROR),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final TossErrorStrategy tossErrorStrategy;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
