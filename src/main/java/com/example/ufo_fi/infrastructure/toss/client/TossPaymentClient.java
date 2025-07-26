package com.example.ufo_fi.infrastructure.toss.client;

import com.example.ufo_fi.domain.payment.application.PaymentClient;
import com.example.ufo_fi.domain.payment.dto.request.ConfirmCommand;
import com.example.ufo_fi.domain.payment.dto.response.ConfirmResult;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.infrastructure.toss.config.PaymentConfig;
import com.example.ufo_fi.infrastructure.toss.exception.TossPaymentErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentClient implements PaymentClient {

    private final PaymentConfig paymentConfig;
    private final ObjectMapper objectMapper;

    /**
     * 결제 최종 승인 요청
     */
    @Override
    public ConfirmResult confirmPayment(ConfirmCommand req) {
        try {
            // 1. 요청 바디 JSON 직렬화
            String requestBody = objectMapper.writeValueAsString(req);

            // 2. 요청 구성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(paymentConfig.getConfirmUrl()))
                    .header("Authorization", "Basic " + paymentConfig.getPaymentSecretKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 3. 응답 수신
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // 4. JSON → 객체 파싱
            return parseConfirmResponse(response);

        } catch (IOException | InterruptedException e) {
            log.error("Toss 결제 승인 중 통신 오류", e);
            throw new GlobalException(TossPaymentErrorCode.TOSS_PAYMENT_CONFIRM_COMMUNICATION_ERROR);
        }
    }

    /**
     * 최종 승인 응답 파싱
     */
    private ConfirmResult parseConfirmResponse(HttpResponse<String> response) {
        try {
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonNode json = objectMapper.readTree(response.body());

                String orderId = json.get("orderId").asText();
                String paymentKey = json.get("paymentKey").asText();
                String approvedAt = json.get("approvedAt").asText();
                String method = json.get("method").asText();

                // TODO 응답값은 나중에 더 추가
                return ConfirmResult.from(orderId, paymentKey, approvedAt, method);
            }
            throw new GlobalException(TossPaymentErrorCode.TOSS_PAYMENT_CONFIRM_FAIL);
        } catch (JsonProcessingException e) {
            log.error("토스 응답 JSON 파싱 실패", e);
            throw new GlobalException(TossPaymentErrorCode.TOSS_PAYMENT_CONFIRM_PARSE_FAIL);
        }
    }
}
