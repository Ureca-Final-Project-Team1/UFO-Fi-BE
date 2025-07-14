package com.example.ufo_fi.domain.payment.ui;

import com.example.ufo_fi.domain.payment.dto.PurchaseReq;
import com.example.ufo_fi.domain.payment.dto.PurchaseRes;
import com.example.ufo_fi.domain.useraccount.dto.response.AccountReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface TradePostPurchaseApiSpec {

    @Operation(summary = "구매(Zet <-> Data) API", description = "구매 요청을 한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/posts/purchases")
    ResponseEntity<ResponseBody<PurchaseRes>> readAccount(
            @RequestParam Long userId,
            @RequestBody PurchaseReq purchaseReq
    );
}
