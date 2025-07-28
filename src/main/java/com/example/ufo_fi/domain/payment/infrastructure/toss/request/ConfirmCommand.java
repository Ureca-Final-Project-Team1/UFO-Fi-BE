package com.example.ufo_fi.domain.payment.infrastructure.toss.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmCommand {

    private String paymentKey;
    private String orderId;
    private int amount;

    static public ConfirmCommand of(String paymentKey, String orderId, int amount) {
        return ConfirmCommand.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .amount(amount)
                .build();
    }
}
