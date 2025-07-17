package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPagePlanApiSpec;
import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.plan.service.PlanService;
import com.example.ufo_fi.domain.user.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.domain.user.dto.response.UserPlanReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.domain.user.service.UserService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPagePlanController implements MyPagePlanApiSpec {
    private final PlanService planService;
    private final UserService userService;

    @Override
    public ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
            String carrier
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        planService.readPlans(carrier)));
    }

    @Override
    public ResponseEntity<ResponseBody<UserPlanReadRes>> readUserPlan(
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.readUserPlan(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<UserPlanUpdateRes>> updateUserPlan(
            DefaultUserPrincipal defaultUserPrincipal,
            UserPlanUpdateReq userPlanUpdateReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.updateUserPlan(defaultUserPrincipal.getId(), userPlanUpdateReq)));
    }
}
