package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPageHistoryApiSpec;
import com.example.ufo_fi.domain.tradepost.application.TradePostService;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.domain.tradepost.presentation.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageHistoryController implements MyPageHistoryApiSpec {

    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<SaleHistoriesRes>> readSaleHistories(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.readSaleHistories(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<PurchaseHistoriesRes>> readPurchaseHistories(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.readPurchaseHistories(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<PurchaseHistoryRes>> readPurchaseHistory(
        Long purchaseHistoryId
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.readPurchaseHistory(purchaseHistoryId)));
    }
}
