package com.example.ufo_fi.domain.mypage.controller.api;

import com.example.ufo_fi.domain.tradepost.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.domain.tradepost.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.domain.tradepost.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Mypage API", description = "마이페이지 내역 API")
public interface MyPageHistoryApiSpec {
    @Operation(summary = "판매 내역 조회 API", description = "내 판매 내역 목록을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/sale-histories")
    ResponseEntity<ResponseBody<SaleHistoriesRes>> readSaleHistories(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "구매 내역 조회 API", description = "내 구매 내역을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/purchase-histories")
    ResponseEntity<ResponseBody<PurchaseHistoriesRes>> readPurchaseHistories(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "구매 내역 상세 보기 API", description = "내 구매 내역 상세를 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/purchase-histories/{purchaseHistoryId}")
    ResponseEntity<ResponseBody<PurchaseHistoryRes>> readPurchaseHistory(
            @PathVariable(name = "purchaseHistoryId") Long purchaseHistoryId
    );
}
