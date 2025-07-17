package com.example.ufo_fi.domain.mypage.controller.api;

import com.example.ufo_fi.domain.follow.dto.response.FollowersReadRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingsReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Mypage API", description = "마이페이지 팔로우 API")
public interface MyPageFollowApiSpec {

    @Operation(summary = "내 팔로워 목록 조회 API", description = "내 계정을 팔로우 하는 유저를 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/followers")
    ResponseEntity<ResponseBody<FollowersReadRes>> readFollowers(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "내 팔로잉 목록 조회 API", description = "내가 팔로우 하는 유저 목록을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/followings")
    ResponseEntity<ResponseBody<FollowingsReadRes>> readFollowings(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
