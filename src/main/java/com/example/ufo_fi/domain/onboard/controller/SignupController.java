package com.example.ufo_fi.domain.onboard.controller;

import com.example.ufo_fi.domain.onboard.dto.request.SignupReq;
import com.example.ufo_fi.domain.onboard.service.SignupService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    /**
     * 회원 가입 시 API
     */
    @PutMapping("/onboard/signup")  //첫 회원가입은 userId를 PathVariable로 받을 수 없다.
    public ResponseEntity<ResponseBody<Void>> updateUser(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
            @RequestBody SignupReq signupReq
    ){
        return ResponseEntity.ok(
                ResponseBody.success(
                        signupService.updateUser(defaultUserPrincipal.getId(),signupReq)));
    }
}
