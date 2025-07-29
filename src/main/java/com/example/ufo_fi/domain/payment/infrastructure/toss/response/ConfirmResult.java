package com.example.ufo_fi.domain.payment.infrastructure.toss.response;

import com.example.ufo_fi.domain.payment.domain.payment.PaymentStatus;

public interface ConfirmResult {
    PaymentStatus resultStatus();
}
