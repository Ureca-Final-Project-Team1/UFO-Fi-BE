package com.example.ufo_fi.domain.signup.controller;

import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.plan.service.PlanService;
import com.example.ufo_fi.domain.signup.controller.api.SignupControllerApiSpec;
import com.example.ufo_fi.domain.user.dto.request.SignupReq;
import com.example.ufo_fi.domain.user.dto.response.SignupRes;
import com.example.ufo_fi.domain.user.dto.response.UserRoleReadRes;
import com.example.ufo_fi.domain.user.service.UserService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController implements SignupControllerApiSpec {
    private final PlanService planService;
    private final UserService userService;

    @Override
    public ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
            String rawCarrier
    ){
        return ResponseEntity.ok(
                ResponseBody.success(
                        planService.readPlans(rawCarrier)));
    }

    @Override
    public ResponseEntity<ResponseBody<SignupRes>> signup(
            DefaultUserPrincipal defaultUserPrincipal,
            SignupReq signupReq
    ){
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.updateUserAndUserPlan(defaultUserPrincipal.getId(), signupReq)));
    }

    @Override
    public ResponseEntity<ResponseBody<UserRoleReadRes>> readUserRole(
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.getUserRole(defaultUserPrincipal.getRole())));
    }
}
