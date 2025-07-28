package com.example.ufo_fi.domain.payment.domain;

import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmSuccessResult;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentManager {
    private final EntityManager entityManager;

    @Transactional
    public void updateReady(Payment payment){
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.changeState(PaymentStatus.READY);
    }

    @Transactional
    public void updateInProgress(Payment payment){
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.changeState(PaymentStatus.IN_PROGRESS);
    }

    @Transactional
    public void updateFail(Payment payment){
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.changeState(PaymentStatus.FAIL);
    }

    @Transactional
    public void updateDone(Payment payment){
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.changeState(PaymentStatus.DONE);
    }

    @Transactional
    public void updateTimeout(Payment payment){
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.changeState(PaymentStatus.TIMEOUT);
    }

    @Transactional
    public void updateByConfirmSuccessResult(Payment payment, ConfirmSuccessResult confirmResult) {
        Payment mergedPayment = entityManager.merge(payment);
        mergedPayment.update(confirmResult);
    }
}
