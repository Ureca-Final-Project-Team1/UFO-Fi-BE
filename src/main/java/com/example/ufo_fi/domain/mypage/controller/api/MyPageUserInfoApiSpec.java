package com.example.ufo_fi.domain.mypage.controller.api;

import com.example.ufo_fi.domain.user.dto.response.UserInfoReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Mypage API", description = "마이페이지 유저 정보 API")
public interface MyPageUserInfoApiSpec {

    @Operation(summary = "나의 프로필 조회 API", description = "유저 기본 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage")
    ResponseEntity<ResponseBody<UserInfoReadRes>> readMyPageUserInfo(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
