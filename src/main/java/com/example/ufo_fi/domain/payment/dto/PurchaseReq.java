package com.example.ufo_fi.domain.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PurchaseReq {
    private Long postId;
    private Long sellerId;
    private Integer totalZet;
    private Integer sellMobileDataAmountGB;
}
