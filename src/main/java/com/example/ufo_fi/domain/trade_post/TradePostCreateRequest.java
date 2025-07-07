package com.example.ufo_fi.domain.trade_post;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 거래 게시글 생성 요청 DTO
 */
@Getter
@NoArgsConstructor
public class TradePostCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1~15자 이내여야 합니다.")
    private String title;

    @Min(value = 1, message = "가격은 1원 이상이여야 됩니다.")
    private int price;

    @Min(value = 1, message = "용량은 1GB 이상이어야 됩니다.")
    private int capacity;

    @NotNull
    private CarrierType carrierType;

    @NotNull
    private MobileDataType mobileDataType;
}
