package com.example.ufo_fi.v2.order.presentation.dto.response;

import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostDetailRes;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostFailPurchaseRes;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostBulkPurchaseConfirmRes {

    private int successCount;
    private int failureCount;
    private List<TradePostDetailRes> successPosts;
    private List<TradePostFailPurchaseRes> failPosts;

}
