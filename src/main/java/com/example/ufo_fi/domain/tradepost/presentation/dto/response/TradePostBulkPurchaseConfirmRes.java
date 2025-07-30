package com.example.ufo_fi.domain.tradepost.presentation.dto.response;

import com.example.ufo_fi.domain.tradepost.domain.TradePost;
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

    public static TradePostBulkPurchaseConfirmRes of(List<TradePost> successPosts,
        List<TradePostFailPurchaseRes> failPosts) {

        List<TradePostDetailRes> successPostDetails = successPosts.stream()
            .map(TradePostDetailRes::from)
            .toList();

        return TradePostBulkPurchaseConfirmRes.builder()
            .successCount(successPostDetails.size())
            .failureCount(failPosts.size())
            .successPosts(successPostDetails)
            .failPosts(failPosts)
            .build();
    }
}
