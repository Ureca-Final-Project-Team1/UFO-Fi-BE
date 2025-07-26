package com.example.ufo_fi.domain.payment.application;

import com.example.ufo_fi.domain.payment.dto.request.ConfirmCommand;
import com.example.ufo_fi.domain.payment.dto.response.ConfirmResult;

public interface PaymentClient {
    ConfirmResult confirmPayment(ConfirmCommand request);
}
