package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.entity.TradePostStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TradePostDetailRes {

    private Long postId;
    private String title;
    private Integer totalPrice;
    private Integer sellMobileDataCapacityGb;
    private Carrier carrier;
    private TradePostStatus status;
    private LocalDateTime createdAt;

    private Integer pricePerUnit;
    private MobileDataType mobileDataType;

    public static TradePostDetailRes from(final TradePost tradePost) {

        return TradePostDetailRes.builder()
            .postId(tradePost.getId())
            .title(tradePost.getTitle())
            .sellMobileDataCapacityGb(tradePost.getSellMobileDataCapacityGb())
            .carrier(tradePost.getCarrier())
            .createdAt(tradePost.getCreatedAt())

            .totalPrice(tradePost.getTotalZet())
            .status(tradePost.getTradePostStatus())

            .pricePerUnit(tradePost.getZetPerUnit())
            .mobileDataType(tradePost.getMobileDataType())
            .build();
    }
}
