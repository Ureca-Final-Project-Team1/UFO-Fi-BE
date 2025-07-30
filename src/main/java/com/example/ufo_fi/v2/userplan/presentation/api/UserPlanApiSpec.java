package com.example.ufo_fi.v2.userplan.presentation.api;

import com.example.ufo_fi.v2.userplan.presentation.dto.request.SignupReq;
import com.example.ufo_fi.v2.userplan.presentation.dto.response.SignupRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "UserPlan API", description = "유저 요금제 도메인")
public interface UserPlanApiSpec {

    @Operation(summary = "회원가입 API", description = "유저 정보와 요금제 정보를 포함하여 저장한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/signup")
    ResponseEntity<ResponseBody<SignupRes>> signup(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        @RequestBody @Valid SignupReq signupReq
    );
}
