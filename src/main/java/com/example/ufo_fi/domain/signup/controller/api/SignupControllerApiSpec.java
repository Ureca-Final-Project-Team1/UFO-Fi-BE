package com.example.ufo_fi.domain.signup.controller.api;

import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.user.dto.request.SignupReq;
import com.example.ufo_fi.domain.user.dto.response.SignupRes;
import com.example.ufo_fi.domain.user.dto.response.UserRoleReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Signup API", description = "회원가입 API")
public interface SignupControllerApiSpec {

    @Operation(summary = "요금제 조회 API", description = "요금제 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/plans")
    ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
            @RequestParam(value = "carrier") String rawCarrier
    );

    @Operation(summary = "회원가입 API", description = "유저 정보와 요금제 정보를 포함하여 저장한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/signup")
    ResponseEntity<ResponseBody<SignupRes>> signup(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
            @RequestBody @Valid SignupReq signupReq
    );

    @Operation(summary = "회원가입 API", description = "유저의 ROLE과 핸드폰 번호를 가져온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/signup/user-info")
    ResponseEntity<ResponseBody<UserRoleReadRes>> readUserInfo(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
