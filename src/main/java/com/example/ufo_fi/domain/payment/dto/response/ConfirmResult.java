package com.example.ufo_fi.domain.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmResult {
    private String orderId;
    private String paymentKey;
    private String approvedAt;
    private String method;

    static public ConfirmResult from(String orderId, String paymentKey, String approvedAt, String method) {
        return ConfirmResult.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .approvedAt(approvedAt)
                .method(method)
                .build();
    }
}
