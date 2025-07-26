package com.example.ufo_fi.domain.payment.state;

import com.example.ufo_fi.domain.payment.entity.Payment;

public class EmptyState implements State {
    @Override
    public void proceed(Payment payment) {
        // 아무 작업도 안하는 상태
    }
}
