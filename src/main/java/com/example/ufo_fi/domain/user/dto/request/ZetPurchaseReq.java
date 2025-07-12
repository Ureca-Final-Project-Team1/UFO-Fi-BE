package com.example.ufo_fi.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ZetPurchaseReq {
    private Integer purchaseZet;
    private Integer price;
    private Integer zetAsset;
}
