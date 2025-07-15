package com.example.ufo_fi.domain.signup.controller.api;

import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.user.dto.request.SignupReq;
import com.example.ufo_fi.domain.user.dto.response.SignupRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Signup API", description = "회원가입 API")
public interface SignupControllerApiSpec {

    @Operation(summary = "요금제 조회 API", description = "요금제 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/plans")
    ResponseEntity<ResponseBody<PlansReadRes>> readPlans(
            @RequestParam(value = "carrier") String rawCarrier
    );

    // TODO: 추후 인증 연동 시 @AuthenticationPrincipal 사용하여 userId 추출
    @Operation(summary = "회원가입 API", description = "유저 정보와 요금제 정보를 포함하여 저장한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/signup")
    ResponseEntity<ResponseBody<SignupRes>> signup(
            @RequestParam Long userId,
            @RequestBody @Valid SignupReq signupReq
    );
}
