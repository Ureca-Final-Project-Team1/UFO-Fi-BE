package com.example.ufo_fi.domain.payment.exception;

import com.example.ufo_fi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "주문번호에 해당하는 내역을 찾을 수 없습니다."),
    PAYMENT_VERIFY_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "결제 검증 요청에 실패했습니다.");


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
