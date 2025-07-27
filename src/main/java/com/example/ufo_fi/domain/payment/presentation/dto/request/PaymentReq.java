package com.example.ufo_fi.domain.payment.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentReq {
    private String orderId;
    private String packageName;
    private Integer amount;
}
