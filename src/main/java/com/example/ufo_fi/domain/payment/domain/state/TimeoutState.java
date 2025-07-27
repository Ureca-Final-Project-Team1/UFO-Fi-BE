package com.example.ufo_fi.domain.payment.domain.state;

import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.domain.PaymentManager;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeoutState implements State<Void> {
    private final PaymentManager paymentManager;

    @Override
    public void proceed(Payment payment, Void unused) {

    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.TIMEOUT;
    }
}
