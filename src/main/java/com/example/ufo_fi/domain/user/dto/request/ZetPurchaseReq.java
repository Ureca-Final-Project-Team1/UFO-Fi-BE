package com.example.ufo_fi.domain.user.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ZetPurchaseReq {

    @Min(value = 180, message = "최소 구매 수량은 180ZET 부터 입니다.")
    @NotNull(message = "구매 수량을 필수입니다.")
    private Integer purchaseZet;

    @Min(value = 180, message = "최소 결제 금액은 1800원 부터 입니다.")
    @NotNull(message = "결제 금액은 필수입니다.")
    private Integer price;
}
