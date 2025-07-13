package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPagePlanApiSpec;
import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.plan.service.PlanService;
import com.example.ufo_fi.domain.user.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.domain.user.dto.response.UserPlanReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.domain.user.service.UserService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPagePlanController implements MyPagePlanApiSpec {
    private final UserService userService;
    private final PlanService planService;

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
            Long userId
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.readUserPlan(userId)));
    }

    @Override
    public ResponseEntity<ResponseBody<UserPlanUpdateRes>> updateUserPlan(
            Long userId,
            UserPlanUpdateReq userPlanUpdateReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.updateUserPlan(userId, userPlanUpdateReq)));
    }
}
