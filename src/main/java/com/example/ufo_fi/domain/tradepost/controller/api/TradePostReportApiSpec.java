package com.example.ufo_fi.domain.tradepost.controller.api;

import com.example.ufo_fi.domain.tradepost.dto.request.TradePostReportReq;
import com.example.ufo_fi.domain.tradepost.dto.response.TradePostReportRes;
import com.example.ufo_fi.domain.user.dto.response.AccountCreateRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "TradePost API", description = "거래 게시물 신고 API")
public interface TradePostReportApiSpec {

    @Operation(summary = "거래 게시물 신고 API", description = "거래 게시물을 신고한다.(미완)")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/reports/{postId}")
    ResponseEntity<ResponseBody<TradePostReportRes>> createReport(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "postId") Long postId,
            @RequestBody TradePostReportReq tradePostReportReq
    );
}
