package com.example.ufo_fi.domain.tradepost.controller;

import com.example.ufo_fi.domain.tradepost.controller.api.TradePostReportApiSpec;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostReportReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostReportRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.domain.user.dto.response.AccountCreateRes;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradePostReportController implements TradePostReportApiSpec {
    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<TradePostReportRes>> createReport(
            Long userId,
            Long postId,
            TradePostReportReq tradePostReportReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.createReport(userId, postId, tradePostReportReq)));
    }
}
