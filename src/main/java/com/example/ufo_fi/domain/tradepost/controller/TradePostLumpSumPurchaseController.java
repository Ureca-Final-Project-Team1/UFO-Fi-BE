package com.example.ufo_fi.domain.tradepost.controller;

import com.example.ufo_fi.domain.tradepost.controller.api.TradePostLumpSumPurchaseApiSpec;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradePostLumpSumPurchaseController implements TradePostLumpSumPurchaseApiSpec {
    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<TradePostBulkPurchaseRes>> readLumSumPurchase(
            TradePostBulkPurchaseReq request,
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.readLumSumPurchase(request, defaultUserPrincipal.getId())));
    }
}
