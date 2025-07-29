package com.example.ufo_fi.domain.payment.domain.state;

import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.example.ufo_fi.domain.payment.domain.StateMetaData;
import com.example.ufo_fi.domain.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;

public interface State {
    PaymentStatus status();

    void proceed(Payment payment, StateMetaData stateMetaData);

    default void verifyStatus(Payment payment, PaymentStatus paymentStatus){
        if(!payment.getStatus().equals(paymentStatus)){
            throw new GlobalException(PaymentErrorCode.PAYMENT_STATUS_ERROR);
        }
    }
}
