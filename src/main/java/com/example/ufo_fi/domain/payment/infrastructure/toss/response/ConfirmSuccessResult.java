package com.example.ufo_fi.domain.payment.infrastructure.toss.response;

import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmSuccessResult implements ConfirmResult{
    private String orderId;
    private String paymentKey;
    private LocalDateTime approvedAt;
    private String method;

    @Override
    public PaymentStatus resultStatus() {
        return PaymentStatus.DONE;
    }

    public static ConfirmSuccessResult from(JsonNode jsonNode) {
        return ConfirmSuccessResult.builder()
                .orderId(jsonNode.get("orderId").asText())
                .paymentKey(jsonNode.get("paymentKey").asText())
                .approvedAt(LocalDateTime.parse(jsonNode.get("approvedAt").asText()))
                .method(jsonNode.get("method").asText())
                .build();
    }
}
