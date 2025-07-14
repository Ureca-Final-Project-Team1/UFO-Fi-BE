package com.example.ufo_fi.domain.tradepost.controller.api;

import com.example.ufo_fi.domain.tradepost.dto.request.TradePostCreateReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostFilterReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostSearchReq;
import com.example.ufo_fi.domain.tradepost.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostCommonRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostFilterRes;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostSearchRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "TradePost API", description = "거래 게시물 API")
public interface TradePostApiSpec {

    @Operation(summary = "거래 게시물 등록 API", description = "거래 게시물을 등록한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/posts")
    ResponseEntity<ResponseBody<TradePostCommonRes>> createTradePost(
            @RequestParam Long userId,
            @RequestBody @Valid TradePostCreateReq request
    );

    @Operation(summary = "거래 게시물 목록 조회 API", description = "거래 게시물을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/posts")
    ResponseEntity<ResponseBody<TradePostSearchRes>> readTradePosts(
            @ModelAttribute TradePostSearchReq request,
            @RequestParam Long userId
    );

    @Operation(summary = "거래 게시물 목록 필터 조회 API", description = "거래 게시물을 필터링하여 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/posts/filter")
    ResponseEntity<ResponseBody<TradePostFilterRes>> readFilterPost(
            @RequestParam Long userId,
            @RequestBody TradePostFilterReq request
    );

    @Operation(summary = "거래 게시물 수정 API", description = "거래 게시물을 수정한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PutMapping("/v1/posts/{postId}")
    ResponseEntity<ResponseBody<TradePostCommonRes>> updateTradePost(
            @RequestParam Long userId,
            @PathVariable Long postId,
            @RequestBody @Valid TradePostUpdateReq request
    );

    @Operation(summary = "거래 게시물 삭제 API", description = "거래 게시물을 삭제한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("/v1/posts/{postId}")
    ResponseEntity<ResponseBody<TradePostCommonRes>> deleteTradePost(
            @RequestParam Long userId,
            @PathVariable Long postId
    );
}
