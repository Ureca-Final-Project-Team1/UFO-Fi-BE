package com.example.ufo_fi.domain.tradepost.controller.api;

import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostPurchaseReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostQueryReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostListRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostPurchaseRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "TradePost API", description = "거래 게시물 API")
public interface TradePostApiSpec {

    @Operation(summary = "판매 게시물 생성 API", description = "거래 게시물을 등록한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/posts")
    ResponseEntity<ResponseBody<TradePostCommonRes>> createTradePost(
        @RequestParam Long userId,
        @RequestBody @Valid TradePostCreateReq request
    );

    @Operation(summary = "게시글 전체 조회 API", description = "거래 게시물을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/posts")
    ResponseEntity<ResponseBody<TradePostListRes>> readTradePosts(
        @ParameterObject TradePostQueryReq request,
        @RequestParam Long userId
    );

    @Operation(summary = "판매 게시물 수정 API", description = "거래 게시물을 수정한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PutMapping("/v1/posts/{postId}")
    ResponseEntity<ResponseBody<TradePostCommonRes>> updateTradePost(
        @RequestParam Long userId,
        @PathVariable Long postId,
        @RequestBody @Valid TradePostUpdateReq request
    );

    @Operation(summary = "판매 게시물 삭제 API", description = "거래 게시물을 삭제한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("/v1/posts/{postId}")
    ResponseEntity<ResponseBody<TradePostCommonRes>> deleteTradePost(
        @RequestParam Long userId,
        @PathVariable Long postId
    );

    @Operation(summary = "구매(Zet <-> Data) API", description = "구매한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/posts/purchase")
    ResponseEntity<ResponseBody<TradePostPurchaseRes>> purchase(
            @RequestParam Long userId,
            @RequestBody TradePostPurchaseReq purchaseReq
    );
}
