package com.example.ufo_fi.domain.mypage.controller.api;

import com.example.ufo_fi.domain.user.dto.request.UserNicknameUpdateReq;
import com.example.ufo_fi.domain.user.dto.response.UserInfoReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserNicknameUpdateRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mypage API", description = "마이페이지 유저 정보 API")
public interface MyPageUserInfoApiSpec {

    @Operation(summary = "나의 프로필 조회 API", description = "유저 기본 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage")
    ResponseEntity<ResponseBody<UserInfoReadRes>> readMyPageUserInfo(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "나의 프로필 수정 API", description = "유저 닉네임을 수정한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PatchMapping("/v1/mypage/nickname")
    ResponseEntity<ResponseBody<UserNicknameUpdateRes>> updateMyPageUserNicknames(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
            @RequestBody @Valid UserNicknameUpdateReq userNicknameUpdateReq
    );
}
