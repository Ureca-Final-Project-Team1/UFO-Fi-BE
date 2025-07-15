package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPageHistoryApiSpec;
import com.example.ufo_fi.domain.tradepost.dto.response.PurchaseHistoriesRes;
import com.example.ufo_fi.domain.tradepost.dto.response.PurchaseHistoryRes;
import com.example.ufo_fi.domain.tradepost.dto.response.SaleHistoriesRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageHistoryController implements MyPageHistoryApiSpec {
    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<SaleHistoriesRes>> readSaleHistories(
            Long userId
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.readSaleHistories(userId)));
    }

    @Override
    public ResponseEntity<ResponseBody<PurchaseHistoriesRes>> readPurchaseHistories(Long userId) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.readPurchaseHistories(userId)));
    }

    @Override
    public ResponseEntity<ResponseBody<PurchaseHistoryRes>> readPurchaseHistory(Long userId, Long purchaseHistoryId) {
        return ResponseEntity.ok(
                ResponseBody.success(tradePostService.readPurchaseHistories()));
    }
}
