package com.example.ufo_fi.domain.payment.domain.state;

import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.domain.PaymentManager;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.example.ufo_fi.domain.payment.domain.PaymentVerifier;
import com.example.ufo_fi.domain.payment.domain.StateMetaData;
import com.example.ufo_fi.domain.payment.presentation.dto.request.ConfirmReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadyState implements State {
    private final PaymentManager paymentManager;
    private final PaymentVerifier paymentVerifier;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        validateParam(stateMetaData);

        paymentVerifier.verify(payment, stateMetaData.getConfirmReq());
        paymentManager.updateInProgress(payment);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.READY;
    }
}
