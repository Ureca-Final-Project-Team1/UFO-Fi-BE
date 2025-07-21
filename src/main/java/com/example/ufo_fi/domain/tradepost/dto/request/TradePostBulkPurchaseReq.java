package com.example.ufo_fi.domain.tradepost.dto.request;

import com.example.ufo_fi.domain.tradepost.entity.BulkPurchaseType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradePostBulkPurchaseReq {

    @Schema(description = "원하는 데이터 용량")
    @NotNull(message = "원하는 데이터 용량을 입력해주세요.")
    @Min(value = 0, message = "데이터 용량은 0GB 이상이어야 합니다.")
    @Max(value = 100, message = "데이터 용량은 100GB를 초과할 수 없습니다.")
    private int desiredGb;

    @Schema(description = "최대 가격")
    @NotNull(message = "최대 예산을 입력해주세요.")
    @Min(value = 0, message = "예산은 0원 이상이어야 합니다.")
    @Max(value = 10000000, message = "예산은 10,000,000원을 초과할 수 없습니다.")
    private int maxPrice;

    @Schema(description = "구매 기준 (용량 우선(초과 할 수 있음) 또는 예산(미만 일 수 있음) 우선)", example = "CAPACITY")
    @NotNull(message = "구매 기준을 선택해주세요.")
    private BulkPurchaseType purchaseType;
}
