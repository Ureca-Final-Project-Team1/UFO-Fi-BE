package com.example.ufo_fi.domain.notification.controller.api;

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

    @Operation(summary = "관심 상품 등록 조건 설정 API", description = "관심 있는 상품의 알림을 받기 위한 조건을 업데이트합니다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PatchMapping("/v1/notification-filters/interested-post")
    ResponseEntity<ResponseBody<Void>> updateInterestedPost(
            @RequestBody InterestedPostUpdateReq request,
            @RequestParam Long userId);
}
