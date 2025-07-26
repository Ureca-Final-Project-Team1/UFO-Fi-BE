package com.example.ufo_fi.infrastructure.toss.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TossPaymentErrorCode implements ErrorCode {
    TOSS_PAYMENT_CONFIRM_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "결제 승인 요청에 실패했습니다."),
    TOSS_PAYMENT_CONFIRM_COMMUNICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Toss 결제 승인 중 통신 오류가 발생했습니다."),
    TOSS_PAYMENT_CONFIRM_PARSE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "승인 응답 파싱에 실패했습니다.");

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
