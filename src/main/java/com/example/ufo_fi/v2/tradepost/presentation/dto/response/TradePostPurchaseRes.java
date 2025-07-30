package com.example.ufo_fi.v2.tradepost.presentation.dto.response;

import com.example.ufo_fi.v2.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostPurchaseRes {

    @Schema(description = "남은 ZET 총량")
    private Integer zetAsset;

    public static TradePostPurchaseRes from(User buyer) {
        return TradePostPurchaseRes.builder()
            .zetAsset(buyer.getZetAsset())
            .build();
    }
}
