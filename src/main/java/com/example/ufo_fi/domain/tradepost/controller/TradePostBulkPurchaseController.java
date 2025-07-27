package com.example.ufo_fi.domain.tradepost.controller;

import com.example.ufo_fi.domain.tradepost.controller.api.TradePostBulkPurchaseApiSpec;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostConfirmBulkReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostBulkPurchaseConfirmRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradePostBulkPurchaseController implements TradePostBulkPurchaseApiSpec {

    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<TradePostBulkPurchaseConfirmRes>> buyBulkPurchase(
        TradePostConfirmBulkReq request,
        DefaultUserPrincipal defaultUserPrincipal) {

        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.bulkPurchase(request, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostBulkPurchaseRes>> readBulkPurchase(
        TradePostBulkPurchaseReq request,
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.readBulkPurchase(request, defaultUserPrincipal.getId())));
    }

}
