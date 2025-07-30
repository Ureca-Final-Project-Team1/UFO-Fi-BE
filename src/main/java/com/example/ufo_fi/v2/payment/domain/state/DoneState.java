package com.example.ufo_fi.v2.payment.domain.state;

import com.example.ufo_fi.v2.payment.domain.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.Payment;
import com.example.ufo_fi.v2.payment.domain.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.StateMetaData;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoneState implements State {
    private final PaymentManager paymentManager;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        verifyStatus(payment, PaymentStatus.DONE);

        ConfirmReq confirmReq = stateMetaData.get(MetaDataKey.CONFIRM_REQUEST, ConfirmReq.class);

        paymentManager.updateUserZetAmount(payment, confirmReq.getAmount());

        stateMetaData.put(MetaDataKey.PAYMENT_DONE, true);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.DONE;
    }
}
