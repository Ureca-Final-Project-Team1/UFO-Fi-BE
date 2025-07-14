package com.example.ufo_fi.domain.tradepost.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradePostPurchaseReq {
    private Long postId;
    private Long sellerId;
    private Integer totalZet;
    private Integer sellMobileDataAmountGB;
}
