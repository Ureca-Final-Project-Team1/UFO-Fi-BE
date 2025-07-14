package com.example.ufo_fi.domain.tradepost.controller.api;

import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "TradePost API", description = "거래 게시물 일괄 구매 API")
public interface TradePostLumpSumPurchaseApiSpec {
    @Operation(summary = "일괄 구매 조회 API", description = "일괄 구매를 위한 조회를 한다.(미완)")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/posts/lump-sum-purchase")
    ResponseEntity<ResponseBody<?>> createAccount(
            @RequestParam Integer purchaseAmount,
            @RequestParam Integer zetPerUnit
    );
}
