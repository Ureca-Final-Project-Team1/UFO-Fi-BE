package com.example.ufo_fi.domain.payment.domain.state;

import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.domain.PaymentManager;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.example.ufo_fi.domain.payment.application.PaymentClient;
import com.example.ufo_fi.domain.payment.domain.StateMetaData;
import com.example.ufo_fi.domain.payment.infrastructure.toss.request.ConfirmCommand;
import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InProgressState implements State {
    private final PaymentClient paymentClient;
    private final PaymentManager paymentManager;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        ConfirmCommand confirmCommand = createConfirmCommand(payment);
        ConfirmResult confirmResult = paymentClient.confirmPayment(confirmCommand);
        updatePaymentByConfirmResult(payment, confirmResult);

        if(confirmResult.resultStatus().equals(PaymentStatus.DONE)) {
            paymentManager.updateDone(payment);
            return;
        }
        if(confirmResult.resultStatus().equals(PaymentStatus.FAIL)) {
            paymentManager.updateFail(payment);
            return;
        }
        paymentManager.updateTimeout(payment);
    }


    @Override
    public PaymentStatus status() {
        return PaymentStatus.IN_PROGRESS;
    }

    private ConfirmCommand createConfirmCommand(Payment payment){
        return ConfirmCommand.of(
                payment.getPaymentKey(),
                payment.getOrderId(),
                payment.getAmount()
        );
    }

    private void updatePaymentByConfirmResult(Payment payment, ConfirmResult confirmResult){
        if(confirmResult instanceof ConfirmSuccessResult){
            paymentManager.updateByConfirmSuccessResult(
                    payment,
                    (ConfirmSuccessResult) confirmResult
            );
        }
    }
}
