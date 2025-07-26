package com.example.ufo_fi.domain.payment.state;

import com.example.ufo_fi.domain.payment.entity.Payment;

public interface State {
    void proceed(Payment payment);
}
