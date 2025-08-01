package com.example.ufo_fi.domain.payment.application;

import com.example.ufo_fi.domain.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.domain.payment.domain.payment.PaymentStateContext;
import com.example.ufo_fi.domain.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.domain.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.domain.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.domain.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.domain.payment.presentation.dto.request.SlackRecoverConfirmReq;
import com.example.ufo_fi.domain.payment.presentation.dto.request.SlackRecoverReq;
import com.example.ufo_fi.domain.payment.presentation.dto.request.SlackRecoverUserReq;
import com.example.ufo_fi.domain.payment.presentation.dto.response.ConfirmRes;
import com.example.ufo_fi.domain.payment.presentation.dto.response.PaymentRes;
import com.example.ufo_fi.domain.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.domain.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.domain.payment.infrastructure.mysql.PaymentRepository;
import com.example.ufo_fi.domain.payment.presentation.dto.response.SlackRecoverRes;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ObjectMapper objectMapper;
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

        Payment payment = Payment.of(user, paymentReq, PaymentStatus.READY, 0);
        paymentRepository.save(payment);

        return PaymentRes.of(user, payment);
    }

    /**
     * 1. payment 엔티티를 찾아온다.
     * 2. stateMetaData 생성
     * 3. proceedAll() => 상태 실행
     * 4. response 응답
     */
    public ConfirmRes confirm(ConfirmReq confirmReq) {
        Payment payment = findPayment(confirmReq.getOrderId());

        StateMetaData stateMetaData = createStateMetaData();
        stateMetaData.put(MetaDataKey.CONFIRM_REQUEST, confirmReq);

        paymentStateContext.proceedAll(payment, stateMetaData);

        User user = payment.getUser();
        return ConfirmRes.of(payment, user);
    }

    private StateMetaData createStateMetaData() {
        return new StateMetaData();
    }

    private Payment findPayment(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new GlobalException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }

    @Transactional
    public SlackRecoverRes recover(SlackRecoverReq slackRecoverReq) {
        try{
            JsonNode jsonNode = objectMapper.readTree(slackRecoverReq.getText());

            SlackRecoverConfirmReq slackRecoverConfirmReq = objectMapper
                    .treeToValue(jsonNode.get(0), SlackRecoverConfirmReq.class);
            SlackRecoverUserReq slackRecoverUserReq = objectMapper
                    .treeToValue(jsonNode.get(1), SlackRecoverUserReq.class);

            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
