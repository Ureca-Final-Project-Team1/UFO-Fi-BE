package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SaleHistorySoldOutRes extends SaleHistoryRes{
    private LocalDateTime saleDateTime;
    private Integer remainZet;

    public static SaleHistorySoldOutRes from(final TradeHistory history) {
        return SaleHistorySoldOutRes.builder()
                .postId(history.getTradePost().getId())
                .status(history.getTradePost().getTradePostStatus())
                .createdAt(history.getTradePost().getCreatedAt())
                .carrier(history.getTradePost().getCarrier())
                .title(history.getTradePost().getTitle())
                .totalZet(history.getTradePost().getTotalZet())
                .mobileDataType(history.getTradePost().getMobileDataType())
                .saleDateTime(history.getCreatedAt())
                .remainZet(history.getUser().getZetAsset())
                .build();
    }
}