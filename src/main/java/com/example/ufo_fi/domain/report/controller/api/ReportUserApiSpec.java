package com.example.ufo_fi.domain.report.controller.api;

import com.example.ufo_fi.domain.report.dto.request.GrantUserRoleReq;
import com.example.ufo_fi.domain.report.dto.response.ReportedUsersReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Report API", description = "신고 관련 API")
public interface ReportUserApiSpec {

    @Operation(summary = "비활성 사용자 조회 API", description = "정지된 사용자 목록을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/users/reported")
    ResponseEntity<ResponseBody<ReportedUsersReadRes>> readReportedUser();

    @Operation(summary = "사용자 비활성화 풀기 API", description = "정지된 사용자의 비활성화를 푼다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PutMapping("/v1/user/grant-user")
    ResponseEntity<ResponseBody<Void>> updateUserRoleUser(
            @RequestBody GrantUserRoleReq grantUserRoleReq
    );
}
