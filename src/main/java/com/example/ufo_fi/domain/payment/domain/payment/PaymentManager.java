package com.example.ufo_fi.domain.payment.domain.payment;

import com.example.ufo_fi.domain.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmSuccessResult;
import com.example.ufo_fi.domain.report.repository.ReportRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentManager {
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

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

    @Transactional
    public void updateUserZetAmount(Payment payment, Integer zetAmount){
        Payment mergedPayment = entityManager.merge(payment);
        User user = mergedPayment.getUser();
        user.increaseZetAsset(zetAmount);
    }

    //유저의 아이디 삭제 + 유저 신고 사유 생성
    @Transactional
    public void updateUserReported(Payment payment) {
        User user = entityManager.merge(payment.getUser());
        user.updateStatusReported();
    }

    @Transactional
    public boolean retry(Payment payment, Integer maxRetryCount) {
        Payment mergedPayment = entityManager.merge(payment);
        if(mergedPayment.getRetryCount() >= maxRetryCount){
            return false;
        }
        mergedPayment.increaseRetryCount();
        mergedPayment.changeState(PaymentStatus.IN_PROGRESS);
        return true;
    }
}
