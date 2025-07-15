package com.example.ufo_fi.domain.follow.controller.api;

import com.example.ufo_fi.domain.follow.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingCreateRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Follow API", description = "팔로워 팔로잉 API")
public interface FollowApiSpec {

    @Operation(summary = "팔로워 팔로잉 맺기 API", description = "다른 유저에게 팔로우를 신청한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("v1/follow/{followingId}")
    ResponseEntity<ResponseBody<FollowingCreateRes>> createFollower(
        @RequestParam("followingId") Long followingId,
        @RequestParam("followerId") Long followerId);

    @Operation(summary = "팔로워 팔로잉 끊기 API", description = "다른 유저에게 팔로우를 취소한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("v1/unfollow/{followerId}")
    ResponseEntity<ResponseBody<FollowerDeleteRes>> deleteFollower(
        @RequestParam("followerId") Long followerId,
        @RequestParam("userId") Long userId);

}
