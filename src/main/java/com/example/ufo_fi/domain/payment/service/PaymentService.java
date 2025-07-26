package com.example.ufo_fi.domain.payment.service;

import com.example.ufo_fi.domain.payment.application.PaymentClient;
import com.example.ufo_fi.domain.payment.dto.request.ConfirmCommand;
import com.example.ufo_fi.domain.payment.dto.request.ConfirmReq;
import com.example.ufo_fi.domain.payment.dto.request.PaymentReq;
import com.example.ufo_fi.domain.payment.dto.response.ConfirmRes;
import com.example.ufo_fi.domain.payment.dto.response.ConfirmResult;
import com.example.ufo_fi.domain.payment.dto.response.PaymentRes;
import com.example.ufo_fi.domain.payment.entity.Payment;
import com.example.ufo_fi.domain.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.domain.payment.repository.PaymentRepository;
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
    private final PaymentClient paymentClient;

    /**
     * 결제 임시 정보 DB 저장
     * 결제 임시 정보 반환
     */
    @Transactional
    public PaymentRes charge(Long userId, PaymentReq request) {

        // 결제 임시 정보 저장
        User user = userRepository.getReferenceById(userId);

        Payment paymentDraft = Payment.of(request, user);
        paymentRepository.save(paymentDraft);

        // 결제 임시 반환
        return PaymentRes.from(user, paymentDraft.getOrderId(), paymentDraft.getAmount());
    }

    /**
     * 결제 정보 검증 -> 아닐 시 에러
     * 최종 결제 승인 요청
     * 충전 내역 업데이트
     * 필요한 정보 반환
     */
    @Transactional
    public ConfirmRes confirm(ConfirmReq request) {

        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new GlobalException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        // 1. 결제 정보 검증
        if (!verify(payment, request)) throw new GlobalException(PaymentErrorCode.PAYMENT_VERIFY_FAIL);

        // 2 .결제 승인 요청
        ConfirmCommand command = ConfirmCommand.from(request.getPaymentKey(), request.getOrderId(), request.getAmount());
        ConfirmResult tossRes = paymentClient.confirmPayment(command);

        // 3. 결제 내용 DB 저장
        updateConfirmedPayment(tossRes);

        // 4. ZET 충전
        // TODO: 실제 ZET 충전되어야 함

        // 5. 응답 반환
        // TODO 넘겨줄 DTO 값 정하기
        return ConfirmRes.from("");
    }

    /**
     * 결제 정보 검증
     * 결제 임시 정보와 최종 승인할 결제 정보 비교
     * 일치할 경우 true
     * 일치하지 않을 경우 false
     */
    private boolean verify(Payment payment, ConfirmReq request) {

        // 주문번호 및 가격 비교
        if (payment.getOrderId().equals(request.getOrderId()) && payment.getAmount() == request.getAmount())
            return true;
        return false;
    }

    /**
     * 결제 최종 승인 정보 업데이트
     */
    void updateConfirmedPayment(ConfirmResult result) {

        Payment payment = paymentRepository.findByOrderId(result.getOrderId())
                .orElseThrow(() -> new GlobalException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        // 상태 업데이트 및 승인 정보 저장
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        payment.update(result.getPaymentKey(), result.getMethod(), LocalDateTime.parse(result.getApprovedAt(), formatter));
    }
}
