package com.example.ufo_fi.domain.payment.state;

import com.example.ufo_fi.domain.payment.entity.Payment;

public class DoneState implements State {
    @Override
    public void proceed(Payment payment) {
        // 유효성 검증, 외부 API 호출 등
        System.out.println("DoneState → ?");
    }
}
