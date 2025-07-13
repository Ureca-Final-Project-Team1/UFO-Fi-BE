package com.example.ufo_fi.domain.notification.controller;

import com.example.ufo_fi.domain.notification.dto.request.FcmTokenSaveReq;
import com.example.ufo_fi.domain.notification.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.global.response.ResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Fcm API", description = "FCM 토큰 관련 API")
public interface FcmControllerApiSpec {

    // TODO: 추후 인증 연동 시 @AuthenticationPrincipal 사용하여 userId 추출
    @Operation(summary = "fcm 토큰 저장 API", description = "fcm 토큰을 프론트 측에서 받아온다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("v1/fcm/token")
    ResponseEntity<ResponseBody<FcmTokenCommonRes>> saveToken(@RequestBody FcmTokenSaveReq request);
}
