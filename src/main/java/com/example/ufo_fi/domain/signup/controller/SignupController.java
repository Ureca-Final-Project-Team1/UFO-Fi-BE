package com.example.ufo_fi.domain.signup.controller;

import com.example.ufo_fi.domain.signup.dto.request.SignupReq;
import com.example.ufo_fi.domain.signup.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.signup.dto.response.SignupRes;
import com.example.ufo_fi.domain.signup.service.SignupService;
import com.example.ufo_fi.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController implements SignupControllerApiSpec{
    private final SignupService signupService;

    @Override
    public ResponseEntity<ResponseBody<PlansReadRes>> readPlans(@RequestParam(value = "carrier") String rawCarrier
    ){
        return ResponseEntity.ok(ResponseBody.success(signupService.readPlans(rawCarrier)));
    }

    @Override
    public ResponseEntity<ResponseBody<SignupRes>> signup(@RequestParam Long userId,
                                                          @RequestBody @Valid SignupReq signupReq
    ){
        return ResponseEntity.ok(ResponseBody.success(signupService.updateUserAndUserPlan(userId, signupReq)));
    }
}
