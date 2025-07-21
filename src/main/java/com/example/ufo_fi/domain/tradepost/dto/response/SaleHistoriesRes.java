package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradeHistory;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.example.ufo_fi.domain.tradepost.entity.TradePostStatus.*;

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
                                    TradePostStatus tradePostStatus = history.getTradePost().getTradePostStatus();
                                    if(SOLD_OUT.equals(tradePostStatus)){
                                        return SaleHistorySoldOutRes.from(history);
                                    }
                                    if(EXPIRED.equals(tradePostStatus)){
                                        return SaleHistoryExpiredRes.from(history);
                                    }
                                    if(REPORTED.equals(tradePostStatus)){
                                        return SaleHistoryReportedRes.from(history);
                                    }
                                    if(SELLING.equals(tradePostStatus)){
                                        return SaleHistoryRes.from(history);
                                    }
                                    throw new GlobalException(TradePostErrorCode.DTO_PARSING_ERROR);
                                })
                                .toList()
                )
                .build();
    }
}