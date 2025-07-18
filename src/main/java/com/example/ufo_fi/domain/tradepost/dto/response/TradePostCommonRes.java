package com.example.ufo_fi.domain.tradepost.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TradePostCommonRes(
        @Schema(description = "게시글 식별 번호")
        Long id) {

}
