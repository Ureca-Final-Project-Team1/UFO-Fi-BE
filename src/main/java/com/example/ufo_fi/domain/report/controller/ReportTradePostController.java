package com.example.ufo_fi.domain.report.controller;

import com.example.ufo_fi.domain.report.controller.api.ReportTradePostApiSpec;
import com.example.ufo_fi.domain.report.dto.request.ReportCreateReq;
import com.example.ufo_fi.domain.report.dto.request.ReportRollBackReq;
import com.example.ufo_fi.domain.report.dto.response.RollBackReportsReadRes;
import com.example.ufo_fi.domain.report.service.ReportService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportTradePostController implements ReportTradePostApiSpec {

    private final ReportService reportService;

    @Override
    public ResponseEntity<ResponseBody<Void>> reportTradePost(
            DefaultUserPrincipal defaultUserPrincipal,
            Long tradePostId,
            ReportCreateReq reportCreateReq
    ) {
        reportService.reportTradePost(defaultUserPrincipal.getId(), reportCreateReq, tradePostId);
        return ResponseEntity.ok(ResponseBody.noContent());
    }

    @Override
    public ResponseEntity<ResponseBody<RollBackReportsReadRes>> readRollBackRegistration() {
        return ResponseEntity.ok(
                ResponseBody.success(
                        reportService.readRollBackRegistration()));
    }

    @Override
    public ResponseEntity<ResponseBody<Void>> approveRollBackRegistration(
            ReportRollBackReq reportRollBackReq
    ) {
        reportService.approveRollBackRegistration(reportRollBackReq);
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
