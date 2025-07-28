package com.example.ufo_fi.domain.payment.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmReq {
    private String paymentKey;
    private String orderId;
    private int amount;
}
