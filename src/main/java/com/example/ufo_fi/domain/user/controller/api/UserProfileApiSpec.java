package com.example.ufo_fi.domain.user.controller.api;

import com.example.ufo_fi.domain.user.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User API", description = "유저 API")
public interface UserProfileApiSpec {

    @Operation(summary = "상대방 유저 조회 API", description = "유저의 정보를 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/profile/{anotherUserId}")
    ResponseEntity<ResponseBody<AnotherUserInfoReadRes>> readUser(
        @RequestParam(name = "anotherUserId") Long anotherUserId,
        @RequestParam Long userId
    );

}
