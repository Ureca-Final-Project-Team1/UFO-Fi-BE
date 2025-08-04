package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TossErrorCode {
    INVALID_REQUEST("잘못된 요청입니다.", TossErrorStrategy.RETRIABLE),
    INVALID_API_KEY("잘못된 API 키입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    INVALID_CARD_NUMBER("잘못된 카드 번호입니다.", TossErrorStrategy.USER_FIXABLE),
    UNAUTHORIZED_KEY("인증되지 않은 키입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    INVALID_PASSWORD("결제 비밀번호 오류입니다.", TossErrorStrategy.USER_FIXABLE),
    INCORRECT_BASIC_AUTH_FORMAT("Basic 인증 포맷 오류", TossErrorStrategy.IMMEDIATE_FAIL),
    NOT_FOUND_PAYMENT("존재하지 않는 결제 정보입니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    NOT_FOUND_PAYMENT_SESSION("결제 세션이 없습니다.", TossErrorStrategy.IMMEDIATE_FAIL),
    INVALID_STOPPED_CARD("정지된 카드입니다.", TossErrorStrategy.USER_FIXABLE),
    INVALID_CARD_EXPIRATION("카드 유효기간 오류", TossErrorStrategy.USER_FIXABLE),
    CARD_PROCESSING_ERROR("카드사 오류", TossErrorStrategy.USER_FIXABLE),
    INVALID_CARD_LOST_OR_STOLEN("분실/도난 카드", TossErrorStrategy.USER_FIXABLE),
    REJECT_CARD_PAYMENT("한도초과 또는 잔액부족", TossErrorStrategy.USER_FIXABLE),
    REJECT_CARD_COMPANY("카드사 승인 거절", TossErrorStrategy.USER_FIXABLE),
    FDS_ERROR("위험 거래 감지", TossErrorStrategy.REPORTED_USER),
    EXCEED_MAX_CARD_INSTALLMENT_PLAN("할부 개월 수 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    NOT_SUPPORTED_INSTALLMENT_PLAN_CARD_OR_MERCHANT("할부 미지원", TossErrorStrategy.LIMIT_EXCEEDED),
    INVALID_CARD_INSTALLMENT_PLAN("할부 개월 수 오류", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_AMOUNT("금액 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_DAILY_PAYMENT_COUNT("일일 결제 횟수 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_PAYMENT_AMOUNT("결제 금액 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_ONE_DAY_WITHDRAW_AMOUNT("1일 출금 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_ONE_TIME_WITHDRAW_AMOUNT("1회 출금 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_MONTHLY_PAYMENT_AMOUNT("월간 결제 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_AUTH_COUNT("인증 시도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    EXCEED_MAX_ONE_DAY_AMOUNT("일일 한도 초과", TossErrorStrategy.LIMIT_EXCEEDED),
    NOT_SUPPORTED_MONTHLY_INSTALLMENT_PLAN("월 할부 미지원", TossErrorStrategy.LIMIT_EXCEEDED),
    INVALID_UNREGISTERED_SUBMALL("등록되지 않은 서브몰", TossErrorStrategy.SERVER_CONFIG_ERROR),
    NOT_REGISTERED_BUSINESS("등록되지 않은 사업자번호", TossErrorStrategy.SERVER_CONFIG_ERROR),
    NOT_FOUND_TERMINAL_ID("단말기 번호 없음", TossErrorStrategy.SERVER_CONFIG_ERROR),
    INVALID_AUTHORIZE_AUTH("잘못된 인증 방식", TossErrorStrategy.SERVER_CONFIG_ERROR),
    FORBIDDEN_REQUEST("허용되지 않은 요청", TossErrorStrategy.SERVER_CONFIG_ERROR),
    ;

    private final String message;
    private final TossErrorStrategy tossErrorStrategy;
}
