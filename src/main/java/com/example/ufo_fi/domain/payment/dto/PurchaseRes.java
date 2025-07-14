package com.example.ufo_fi.domain.payment.dto;

import com.example.ufo_fi.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRes {
    private Integer zetAsset;

    public static PurchaseRes from(User buyer) {
        return PurchaseRes.builder()
                .zetAsset(buyer.getZetAsset())
                .build();
    }
}
