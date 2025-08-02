package com.example.ufo_fi.v2.payment.application;

import com.example.ufo_fi.v2.payment.persistence.toss.response.ConfirmResult;
import com.example.ufo_fi.v2.payment.persistence.toss.request.ConfirmCommand;

public interface PaymentClient {
    ConfirmResult confirmPayment(ConfirmCommand confirmCommand);
}
