package com.example.ufo_fi.domain.notification.controller;

import com.example.ufo_fi.domain.notification.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "InterestedPost API", description = "관심 상품 알림 API")
public interface InterestedPostApiSpec {

    @Operation(summary = "fcm 토큰 저장 API", description = "fcm 토큰을 프론트 측에서 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PatchMapping("/v1/notification-filters/interested-post")
    ResponseEntity<ResponseBody<Void>> updateInterestedPost(
            @RequestBody InterestedPostUpdateReq request,
            @RequestParam Long userId);
}
