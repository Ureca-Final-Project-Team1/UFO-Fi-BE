package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostSearchDetailRes {

    private String title;
    private Carrier carrier;
    private Integer pricePerUnit;
    private MobileDataType mobileDataType;
    private Integer sellMobileDataCapacityGb;

    public static TradePostSearchDetailRes from(final TradePost tradePost) {

        return TradePostSearchDetailRes.builder()
            .title(tradePost.getTitle())
            .carrier(tradePost.getCarrier())
            .pricePerUnit(tradePost.getZetPerUnit())
            .mobileDataType(tradePost.getMobileDataType())
            .sellMobileDataCapacityGb(tradePost.getSellMobileDataCapacityGb())
            .build();
    }
}
