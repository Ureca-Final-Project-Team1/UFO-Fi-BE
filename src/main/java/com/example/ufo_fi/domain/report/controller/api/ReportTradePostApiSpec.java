package com.example.ufo_fi.domain.report.controller.api;

import com.example.ufo_fi.domain.report.dto.request.ReportCreateReq;
import com.example.ufo_fi.domain.report.dto.request.ReportRollBackReq;
import com.example.ufo_fi.domain.report.dto.response.RollBackReportsReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Report API", description = "신고 관련 API")
public interface ReportTradePostApiSpec {

    @Operation(summary = "신고하기 API", description = "신고를 한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/trade-posts/{tradePostId}/report")
    ResponseEntity<ResponseBody<Void>> reportTradePost(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
            @RequestBody ReportCreateReq reportCreateReq
    );

    @Operation(summary = "관리자용 신고된 게시물 조회 API", description = "관리자는 신고된 게시물들을 볼 수 있다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/admin/trade-posts/reported")
    ResponseEntity<ResponseBody<RollBackReportsReadRes>> readRollBackRegistration();

    @Operation(summary = "관리자용 신고 해지하기 API", description = "관리자는 사용자의 신고 해지 요청에 대해 신고를 해지할 수 있다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PutMapping("/v1/admin/roll-back-report")
    ResponseEntity<ResponseBody<Void>> approveRollBackRegistration(
            @RequestBody ReportRollBackReq reportRollBackReq
    );
}
