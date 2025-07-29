package com.example.ufo_fi.domain.payment.domain.payment.state;

import com.example.ufo_fi.domain.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.domain.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.domain.payment.domain.payment.PaymentManager;
import com.example.ufo_fi.domain.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.domain.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.domain.payment.exception.TossPaymentErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.global.log.LogTraceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeoutState implements State {
    private static final Integer MAX_RETRY_COUNT = 2;

    private final PaymentManager paymentManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData){
        verifyStatus(payment, PaymentStatus.TIMEOUT);

        boolean canRetry = paymentManager.retry(payment, MAX_RETRY_COUNT);

        try{
            if(canRetry){
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GlobalException(TossPaymentErrorCode.TOSS_PAYMENT_CONFIRM_FAIL);
        }

        stateMetaData.put(MetaDataKey.PAYMENT_DONE, true);
        applicationEventPublisher.publishEvent(LogTraceContext.get());
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.TIMEOUT;
    }
}
