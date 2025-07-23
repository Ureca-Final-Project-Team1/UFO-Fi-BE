package com.example.ufo_fi.domain.tradepost.controller;


import com.example.ufo_fi.domain.tradepost.controller.api.TradePostApiSpec;
import com.example.ufo_fi.domain.tradepost.dto.request.*;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostListRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostPurchaseRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostReadRes;
import com.example.ufo_fi.domain.tradepost.service.TradePostService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TradePostController implements TradePostApiSpec {

    private final TradePostService tradePostService;

    @Override
    public ResponseEntity<ResponseBody<TradePostCommonRes>> createTradePost(
            DefaultUserPrincipal defaultUserPrincipal,
            TradePostCreateReq request
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.createTradePost(request, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostListRes>> readTradePosts(
            TradePostQueryReq request,
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.readTradePostList(request, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostCommonRes>> updateTradePost(
            DefaultUserPrincipal defaultUserPrincipal,
            Long postId,
            TradePostUpdateReq request
    ) {

        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.updateTradePost(postId, request, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostCommonRes>> deleteTradePost(
            DefaultUserPrincipal defaultUserPrincipal,
            Long postId
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                tradePostService.deleteTradePost(postId, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostPurchaseRes>> purchase(
            DefaultUserPrincipal defaultUserPrincipal,
            TradePostPurchaseReq purchaseReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.purchase(defaultUserPrincipal.getId(), purchaseReq)));
    }

    @Override
    public ResponseEntity<ResponseBody<TradePostReadRes>> readTradePost(
        DefaultUserPrincipal defaultUserPrincipal,
        Long postId
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        tradePostService.readTradePost(defaultUserPrincipal.getId(), postId)));
    }
}
