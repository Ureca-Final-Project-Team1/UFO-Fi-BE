package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TradePostBulkPurchaseRes {

    private final int totalGb;
    private final int totalPrice;
    private final List<TradePostSearchDetailRes> posts;

    public static TradePostBulkPurchaseRes from(List<TradePost> finalPosts) {

        List<TradePostSearchDetailRes> postDetails = finalPosts.stream()
            .map(TradePostSearchDetailRes::from)
            .collect(Collectors.toList());

        int totalGb = finalPosts.stream().mapToInt(TradePost::getSellMobileDataCapacityGb).sum();
        int totalPrice = finalPosts.stream().mapToInt(TradePost::getTotalZet).sum();

        return TradePostBulkPurchaseRes.builder()
            .totalGb(totalGb)
            .totalPrice(totalPrice)
            .posts(postDetails)
            .build();
    }

}
