package com.example.ufo_fi.domain.payment.domain.state;

import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.example.ufo_fi.domain.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;

public interface State<T> {
    PaymentStatus status();

    void proceed(Payment payment, T param);

    default void validateParam(T param){
        if(param == null){
            throw new GlobalException(PaymentErrorCode.CONFIRM_REQUEST_NULL);
        }
    }
}
