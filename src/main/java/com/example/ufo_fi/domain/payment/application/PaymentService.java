package com.example.ufo_fi.domain.payment.application;

import com.example.ufo_fi.domain.payment.domain.PaymentStateContext;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.example.ufo_fi.domain.payment.domain.PaymentVerifier;
import com.example.ufo_fi.domain.payment.domain.StateMetaData;
import com.example.ufo_fi.domain.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.domain.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.domain.payment.presentation.dto.response.ConfirmRes;
import com.example.ufo_fi.domain.payment.infrastructure.toss.response.ConfirmSuccessResult;
import com.example.ufo_fi.domain.payment.presentation.dto.response.PaymentRes;
import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.domain.payment.infrastructure.mysql.PaymentRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStateContext paymentStateContext;

    /**
     * 결제 임시 정보 DB 저장
     * 결제 임시 정보 반환
     */
    @Transactional
    public PaymentRes charge(Long userId, PaymentReq paymentReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(PaymentErrorCode.NO_USER));

        Payment payment = Payment.of(user, paymentReq, PaymentStatus.READY);
        paymentRepository.save(payment);

        return PaymentRes.of(user, payment.getOrderId(), payment.getAmount());
    }

    /**
     * 결제 정보 검증 -> 아닐 시 에러
     * 최종 결제 승인 요청
     * 충전 내역 업데이트
     * 필요한 정보 반환
     */
    public ConfirmRes confirm(ConfirmReq confirmReq) {
        Payment payment = findPayment(confirmReq.getOrderId());
        StateMetaData stateMetaData = getStateMetaData();
        stateMetaData.setConfirmReq(confirmReq);
        paymentStateContext.proceedAll(payment, stateMetaData);
        return ConfirmRes.from(payment);
    }

    private Payment findPayment(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new GlobalException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }

    private StateMetaData getStateMetaData(){
        return new StateMetaData();
    }
}
