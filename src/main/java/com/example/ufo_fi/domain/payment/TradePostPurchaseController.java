package com.example.ufo_fi.domain.payment;

import com.example.ufo_fi.domain.payment.dto.PurchaseReq;
import com.example.ufo_fi.domain.payment.dto.PurchaseRes;
import com.example.ufo_fi.domain.payment.ui.TradePostPurchaseApiSpec;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradePostPurchaseController implements TradePostPurchaseApiSpec {
    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<PurchaseRes>> readAccount(
            Long userId,
            PurchaseReq purchaseReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.purchase(userId, purchaseReq)));
    }
}
