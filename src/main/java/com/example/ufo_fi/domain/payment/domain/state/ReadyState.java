package com.example.ufo_fi.domain.payment.domain.state;

import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.domain.PaymentManager;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.example.ufo_fi.domain.payment.domain.PaymentVerifier;
import com.example.ufo_fi.domain.payment.presentation.dto.request.ConfirmReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadyState implements State<ConfirmReq> {
    private final PaymentManager paymentManager;
    private final PaymentVerifier paymentVerifier;

    @Override
    public void proceed(Payment payment, ConfirmReq confirmReq) {
        validateParam(confirmReq);

        paymentVerifier.verify(payment, confirmReq);
        paymentManager.updateInProgress(payment);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.READY;
    }
}
