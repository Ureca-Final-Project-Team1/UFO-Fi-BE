package com.example.ufo_fi.v2.payment.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SlackRecoverConfirmReq {
    private String paymentKey;
    private String orderId;
    private Integer amount;
    private Integer price;
}
