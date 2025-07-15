package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoriesRes {
    private List<PurchaseHistoryRes> purchaseHistoriesRes;

    public static PurchaseHistoriesRes from(final List<TradeHistory> tradeHistories) {
        return PurchaseHistoriesRes.builder()
                .purchaseHistoriesRes(tradeHistories.stream().map(PurchaseHistoryRes::from).toList())
                .build();
    }
}
