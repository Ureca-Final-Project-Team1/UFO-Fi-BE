package com.example.ufo_fi.domain.tradepost.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 거래 게시글 생성 요청 DTO
 */
@Getter
@NoArgsConstructor
public class TradePostCreateReq {

    @Schema(description = "판매글 제목", example = "급처분합니다.")
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1~15자 이내여야 합니다.")
    private String title;

    @Schema(description = "판매 가격", example = "120")
    @Min(value = 1, message = "가격은 1ZET 이상이여야 됩니다.")
    private Integer price;

    @Schema(description = "판매할 데이터 용량", example = "10")
    @Min(value = 1, message = "용량은 1GB 이상이어야 됩니다.")
    private Integer sellMobileDataCapacityGb;
}
