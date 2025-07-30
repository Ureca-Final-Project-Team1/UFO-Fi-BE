package com.example.ufo_fi.v2.payment.domain.state;

import com.example.ufo_fi.v2.payment.domain.Payment;
import com.example.ufo_fi.v2.payment.domain.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.StateMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeoutState implements State {
    private final PaymentManager paymentManager;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        verifyStatus(payment, PaymentStatus.TIMEOUT);


    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.TIMEOUT;
    }
}
