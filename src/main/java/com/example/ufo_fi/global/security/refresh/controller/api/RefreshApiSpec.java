package com.example.ufo_fi.global.security.refresh.controller.api;

import com.example.ufo_fi.domain.user.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Refresh API", description = "리프레시 API")
public interface RefreshApiSpec {

        @Operation(summary = "jwt 토큰 갱신 API", description = "jwt 토큰을 갱신한다.")
        @ApiResponse(useReturnTypeSchema = true)
        @GetMapping("/v1/refresh")
        ResponseEntity<ResponseBody<AnotherUserInfoReadRes>> readUser(
                @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
                HttpServletRequest request,
                HttpServletResponse response
        );
}

