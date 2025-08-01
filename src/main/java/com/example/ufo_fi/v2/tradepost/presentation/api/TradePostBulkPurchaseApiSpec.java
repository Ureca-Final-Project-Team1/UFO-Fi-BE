package com.example.ufo_fi.v2.tradepost.presentation.api;


import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostBulkPurchaseRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "TradePost API", description = "거래 게시물 일괄 구매 API")
public interface TradePostBulkPurchaseApiSpec {

    @Operation(summary = "일괄 구매 조회 API", description = "일괄 구매를 위한 조회를 한다.(미완)")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/posts/bulk-purchase")
    ResponseEntity<ResponseBody<TradePostBulkPurchaseRes>> readBulkPurchase(
        @ParameterObject TradePostBulkPurchaseReq request,
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
