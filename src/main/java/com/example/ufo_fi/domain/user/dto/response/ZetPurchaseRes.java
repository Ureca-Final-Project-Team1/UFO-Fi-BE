package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZetPurchaseRes {

    @Schema(description = "충전 후 잔액")
    private Integer zetAsset;

    public static ZetPurchaseRes from(User user) {
        return ZetPurchaseRes.builder()
                .zetAsset(user.getZetAsset())
                .build();
    }
}
