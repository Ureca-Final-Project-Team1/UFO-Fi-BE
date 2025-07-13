package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TradePostFilterDetailRes {

    private Long postId;
    private String title;
    private Integer totalPrice;
    private Integer sellMobileDataCapacityGb;
    private Carrier carrier;
    private TradePostStatus status;
    private LocalDateTime createdAt;

    public static TradePostFilterDetailRes from(final TradePost tradePost) {

        return TradePostFilterDetailRes.builder()
            .postId(tradePost.getId())
            .title(tradePost.getTitle())
            .totalPrice(tradePost.getTotalPrice())
            .sellMobileDataCapacityGb(tradePost.getSellMobileDataCapacityGb())
            .carrier(tradePost.getCarrier())
            .status(tradePost.getTradePostStatus())
            .createdAt(tradePost.getCreatedAt())
            .build();
    }
}