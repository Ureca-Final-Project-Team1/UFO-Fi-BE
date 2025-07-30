package com.example.ufo_fi.domain.mypage.controller.api;

import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.user.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.domain.user.dto.response.UserPlanReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Mypage API", description = "마이페이지 요금제 API")
public interface MyPagePlanApiSpec {

    @Operation(summary = "나의 요금제 정보 조회 API", description = "유저의 요금제 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/user-plan")
    ResponseEntity<ResponseBody<UserPlanReadRes>> readUserPlan(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
            );

    @Operation(summary = "요금제 정보 변경 API", description = "유저의 요금제 정보를 변경한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PutMapping("/v1/mypage/plan")
    ResponseEntity<ResponseBody<UserPlanUpdateRes>> updateUserPlan(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
            @RequestBody UserPlanUpdateReq userPlanUpdateReq
    );
}
