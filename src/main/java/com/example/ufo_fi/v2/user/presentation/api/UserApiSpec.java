package com.example.ufo_fi.v2.user.presentation.api;

import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "User API", description = "유저 도메인")
public interface UserApiSpec {

    @Operation(summary = "회원가입 API", description = "유저의 ROLE과 핸드폰 번호를 가져온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/signup/user-info")
    ResponseEntity<ResponseBody<UserRoleReadRes>> readUserInfo(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
