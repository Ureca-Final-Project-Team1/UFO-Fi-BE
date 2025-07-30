package com.example.ufo_fi.v2.tradepost.presentation.dto.response;

import com.example.ufo_fi.v2.tradepost.domain.TradeHistory;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
