package com.example.ufo_fi.domain.signup.controller.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Logout API", description = "로그아웃 API")
public interface LogoutApiSpec {

    @Operation(summary = "로그아웃 API", description = "JWT와 Refresh 토큰을 삭제한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/logout")
    ResponseEntity<ResponseBody<Void>> logout(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    );
}
