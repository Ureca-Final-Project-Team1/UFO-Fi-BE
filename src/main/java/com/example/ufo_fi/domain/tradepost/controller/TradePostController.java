package com.example.ufo_fi.domain.tradepost.controller;


import com.example.ufo_fi.domain.tradepost.controller.api.TradePostApiSpec;
import com.example.ufo_fi.domain.tradepost.controller.api.TradePostLumpSumPurchaseApiSpec;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostBulkPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostQueryReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostBulkPurchaseRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostListRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TradePostController implements TradePostApiSpec, TradePostLumpSumPurchaseApiSpec {

    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<TradePostCommonRes>> createTradePost(
        Long userId,
        TradePostCreateReq request
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.createTradePost(request, userId)));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostListRes>> readTradePosts(
        TradePostQueryReq request,
        Long userId
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.readTradePostList(request, userId)));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostCommonRes>> updateTradePost(
        Long userId,
        Long postId,
        TradePostUpdateReq request
    ) {

        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.updateTradePost(postId, request, userId)));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostCommonRes>> deleteTradePost(
        Long postId,
        Long userId
    ) {

        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.deleteTradePost(postId, userId)));
    }


    @Override
    public ResponseEntity<ResponseBody<TradePostBulkPurchaseRes>> readLumSumPurchase(
        TradePostBulkPurchaseReq request,
        Long userId) {

        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.readLumSumPurchase(request, userId)
            )
        );
    }
}
