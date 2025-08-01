package com.example.ufo_fi.v2.tradepost.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.tradepost.application.TradePostService;
import com.example.ufo_fi.v2.tradepost.presentation.api.TradePostBulkPurchaseApiSpec;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.v2.tradepost.presentation.dto.response.TradePostBulkPurchaseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradePostBulkPurchaseController implements TradePostBulkPurchaseApiSpec {

    private final TradePostService tradePostService;

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
