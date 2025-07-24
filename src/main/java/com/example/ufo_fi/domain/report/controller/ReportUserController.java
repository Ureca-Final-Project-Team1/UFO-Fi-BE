package com.example.ufo_fi.domain.report.controller;

import com.example.ufo_fi.domain.report.controller.api.ReportUserApiSpec;
import com.example.ufo_fi.domain.report.dto.request.GrantUserRoleReq;
import com.example.ufo_fi.domain.report.dto.response.ReportedUserReadRes;
import com.example.ufo_fi.domain.report.dto.response.ReportedUsersReadRes;
import com.example.ufo_fi.domain.report.service.ReportService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportUserController implements ReportUserApiSpec {

    private final ReportService reportService;

    @Override
    public ResponseEntity<ResponseBody<ReportedUsersReadRes>> readReportedUser() {
        return ResponseEntity.ok(
                ResponseBody.success(
                    reportService.readReportedUser()));
    }

    @Override
    public ResponseEntity<ResponseBody<Void>> updateUserRoleUser(
            GrantUserRoleReq grantUserRoleReq
    ) {
        reportService.updateUserRoleUser(grantUserRoleReq);
        return ResponseEntity.ok(ResponseBody.noContent());
    }


}
