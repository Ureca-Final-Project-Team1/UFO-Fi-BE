package com.example.ufo_fi.domain.onboard.controller;

import com.example.ufo_fi.domain.onboard.dto.request.PlansReadReq;
import com.example.ufo_fi.domain.onboard.dto.request.UserPlanCreateReq;
import com.example.ufo_fi.domain.onboard.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.onboard.service.PlanSelectService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanSelectController {
    private final PlanSelectService planSelectService;

    /**
     * 통신사를 받고, 해당 통신사의 plan들을 가져오는 API
     */
    @GetMapping("/onboard/plan")
    public ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
            @RequestBody PlansReadReq plansReadReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        planSelectService.readPlans(plansReadReq)));
    }

    /**
     * 회원가입 완료 버튼 클릭 시 회원 요금제 테이블에 정보 저장 API
     */
    @PostMapping("/onboard/user-plan")
    public ResponseEntity<ResponseBody<Void>> createUserPlan(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
            @RequestBody UserPlanCreateReq userPlanCreateReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        planSelectService.createUserPlan(defaultUserPrincipal.getId(), userPlanCreateReq)));
    }
}
