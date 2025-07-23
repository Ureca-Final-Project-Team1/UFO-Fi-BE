package com.example.ufo_fi.domain.tradepost.dto.response;

import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostReadRes {

    @Schema(description = "게시글 아이디")
    private Long postId;

    @Schema(description = "총 ZET 가격")
    private Integer totalZet;

    @Schema(description = "판매자 아이디")
    private Long sellerId;

    @Schema(description = "판매 총 GB")
    private Integer sellMobileDataCapacityGb;

    public static TradePostReadRes of(final TradePost tradePost, final Long sellerId){
        return TradePostReadRes.builder()
            .postId(tradePost.getId())
            .totalZet(tradePost.getTotalZet())
            .sellerId(sellerId)
            .sellMobileDataCapacityGb(tradePost.getSellMobileDataCapacityGb())
            .build();
    }
}
