package com.example.ufo_fi.domain.payment.service;

import com.example.ufo_fi.domain.payment.config.PaymentConfig;
import com.example.ufo_fi.domain.payment.dto.request.ConfirmReq;
import com.example.ufo_fi.domain.payment.dto.request.PaymentReq;
import com.example.ufo_fi.domain.payment.dto.response.ConfirmRes;
import com.example.ufo_fi.domain.payment.dto.response.PaymentRes;
import com.example.ufo_fi.domain.payment.entity.Payment;
import com.example.ufo_fi.domain.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.domain.payment.repository.PaymentRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentConfig paymentConfig;

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
        HttpResponse<String> response = requestConfirm(request);

        // 3. 응답 파싱
        // TODO: 응답이 오류일 경우 처리
        JsonNode jsonResponse = jsonParser(response);

        // 4. 결제 내용 DB 저장
        updateConfirmedPayment(jsonResponse);

        // 5. 응답 반환
        // TODO: 넘겨줄 데이터 확정지어 넘겨주기
        String url = jsonResponse.get("receipt").get("url").asText();
        return ConfirmRes.from(url);
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
     * 결제 최종 승인 요청
     */
    private HttpResponse requestConfirm(ConfirmReq req) {
        try {
            String requestBody = new ObjectMapper().writeValueAsString(req);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(paymentConfig.getConfirmUrl()))
                    .header("Authorization", "Basic " + paymentConfig.getPaymentSecretKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new GlobalException(PaymentErrorCode.PAYMENT_CONFIRM_FAIL);
        }
    }

    /**
     * 결제 최종 승인 정보 업데이트
     */
    void updateConfirmedPayment(JsonNode response) {

        // 응답에서 필요한 필드 추출
        String paymentKey = response.get("paymentKey").asText();
        String orderId = response.get("orderId").asText();
        String method = response.get("method").asText();
        String approvedAt = response.get("approvedAt").asText();

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new GlobalException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        // 상태 업데이트 및 승인 정보 저장
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        payment.update(paymentKey, method, LocalDateTime.parse(approvedAt, formatter));
    }

    /**
     * 최종 승인 응답 파싱
     */
    private JsonNode jsonParser(HttpResponse<String> response) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(response.body());
            return jsonNode;
        } catch (IOException e) {
            throw new GlobalException(PaymentErrorCode.PAYMENT_CONFIRM_PARSE_FAIL);
        }
    }
}
