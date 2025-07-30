package com.example.ufo_fi.domain.tradepost.presentation.dto.response;

import static com.example.ufo_fi.domain.tradepost.domain.TradePostStatus.EXPIRED;
import static com.example.ufo_fi.domain.tradepost.domain.TradePostStatus.REPORTED;
import static com.example.ufo_fi.domain.tradepost.domain.TradePostStatus.SELLING;
import static com.example.ufo_fi.domain.tradepost.domain.TradePostStatus.SOLD_OUT;

import com.example.ufo_fi.domain.tradepost.domain.TradeHistory;
import com.example.ufo_fi.domain.tradepost.domain.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleHistoriesRes {

    private List<? extends SaleHistoryRes> saleHistoriesRes;

    public static SaleHistoriesRes from(final List<TradeHistory> tradeHistories) {
        return SaleHistoriesRes.builder()
            .saleHistoriesRes(
                tradeHistories.stream()
                    .map(history -> {
                        TradePostStatus tradePostStatus = history.getTradePost()
                            .getTradePostStatus();
                        if (SOLD_OUT.equals(tradePostStatus)) {
                            return SaleHistorySoldOutRes.from(history);
                        }
                        if (EXPIRED.equals(tradePostStatus)) {
                            return SaleHistoryExpiredRes.from(history);
                        }
                        if (REPORTED.equals(tradePostStatus)) {
                            return SaleHistoryReportedRes.from(history);
                        }
                        if (SELLING.equals(tradePostStatus)) {
                            return SaleHistoryRes.from(history);
                        }
                        throw new GlobalException(TradePostErrorCode.DTO_PARSING_ERROR);
                    })
                    .toList()
            )
            .build();
    }
}