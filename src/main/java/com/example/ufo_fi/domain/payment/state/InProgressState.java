package com.example.ufo_fi.domain.payment.state;

import com.example.ufo_fi.domain.payment.entity.Payment;
import com.example.ufo_fi.domain.payment.entity.PaymentStatus;

public class InProgressState implements State {
    @Override
    public void proceed(Payment payment) {
        // 유효성 검증, 외부 API 호출 등
        System.out.println("Ready → InProgress");

        payment.changeState(PaymentStatus.DONE);
    }
}
