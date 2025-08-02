package com.example.ufo_fi.domain.tradepost.presentation.dto.response;

import com.example.ufo_fi.domain.tradepost.domain.TradePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostPurchaseDetailRes {

    @Schema(description = "거래 게시물 PK입니다.")
    private Long postId;

    @Schema(description = "거래 게시물 GB입니다.")
    private Integer sellMobileDataCapacityGb;

    @Schema(description = "거래 게시물 ZET 가격입니다.")
    private Integer totalZet;

    public static TradePostPurchaseDetailRes of(final TradePost tradePost){
        return TradePostPurchaseDetailRes.builder()
            .postId(tradePost.getId())
            .sellMobileDataCapacityGb(tradePost.getSellMobileDataCapacityGb())
            .totalZet(tradePost.getTotalZet())
            .build();
    }
}
