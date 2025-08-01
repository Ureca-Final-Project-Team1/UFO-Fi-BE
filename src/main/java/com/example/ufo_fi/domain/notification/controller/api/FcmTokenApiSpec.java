package com.example.ufo_fi.domain.notification.controller.api;

import com.example.ufo_fi.domain.notification.dto.request.FcmTokenSaveReq;
import com.example.ufo_fi.domain.notification.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "FCM token API", description = "FCM 토큰 API")
public interface FcmTokenApiSpec {

    @Operation(summary = "FCM 토큰 저장 API", description = "사용자의 FCM 토큰을 저장한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/v1/fcm/token")
    ResponseEntity<ResponseBody<FcmTokenCommonRes>> saveToken(
            @RequestBody FcmTokenSaveReq request,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
