package com.example.ufo_fi.domain.signup.controller;

import com.example.ufo_fi.domain.signup.dto.request.SignupReq;
import com.example.ufo_fi.domain.signup.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.signup.dto.response.SignupRes;
import com.example.ufo_fi.domain.signup.service.SignupService;
import com.example.ufo_fi.global.response.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    /**
     * 통신사를 받아 요금제를 받아오는 API
     */
    @GetMapping("/plans")
    public ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
            @RequestParam(value = "carrier") String rawCarrier
    ){
        return ResponseEntity.ok(
                ResponseBody.success(
                        signupService.readPlans(rawCarrier)));
    }

    /**
     * 회원가입 - 요금제 연동을 맡는 API
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseBody<SignupRes>> signup(
            @RequestParam Long userId,
            @RequestBody @Valid SignupReq signupReq
    ){
        return ResponseEntity.ok(
                ResponseBody.success(
                        signupService.updateUserAndUserPlan(userId, signupReq)));
    }
}
