package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryRes {
    private Long postId;
    private Long purchaseHistoryId;
    private LocalDateTime createdAt;
    private Carrier carrier;
    private String title;
    private Integer totalZet;

    private MobileDataType mobileDataType;

    public static PurchaseHistoryRes from(final TradeHistory tradeHistory) {
        return PurchaseHistoryRes.builder()
                .postId(tradeHistory.getTradePost().getId())
                .purchaseHistoryId(tradeHistory.getId())
                .createdAt(tradeHistory.getCreatedAt())
                .carrier(tradeHistory.getTradePost().getCarrier())
                .title(tradeHistory.getTradePost().getTitle())
                .totalZet(tradeHistory.getTradePost().getTotalZet())
                .mobileDataType(tradeHistory.getTradePost().getMobileDataType())
                .build();
    }
}
