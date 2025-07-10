package com.example.ufo_fi.domain.notification.controller;

import com.example.ufo_fi.domain.notification.dto.request.FcmTokenSaveReq;
import com.example.ufo_fi.domain.notification.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.domain.notification.service.FcmTokenService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    // GetMapping, 해당 사용자의 FCM 토큰을 저장
    @PostMapping("v1/fcm/token")
    public ResponseEntity<ResponseBody<FcmTokenCommonRes>> saveToken(
            // @AuthenticationPrincipal CustomUserDetails customUserDetails
            @RequestBody FcmTokenSaveReq request) {

        // TODO: 추후 인증 연동 시 @AuthenticationPrincipal 사용하여 userId 추출
        Long userId = 1L;

        return ResponseEntity.ok(ResponseBody.success(fcmTokenService.save(userId, request.getToken())));
    }
}
