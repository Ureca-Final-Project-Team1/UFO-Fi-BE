package com.example.ufo_fi.domain.tradepost.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradePostReportReq {
    private String content;
    private Long postOwnerUserId;
}
