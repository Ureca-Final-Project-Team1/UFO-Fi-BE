package com.example.ufo_fi.domain.payment.application;

import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.domain.payment.infrastructure.toss.request.ConfirmCommand;

public interface PaymentClient {
    ConfirmResult confirmPayment(ConfirmCommand confirmCommand);
}
