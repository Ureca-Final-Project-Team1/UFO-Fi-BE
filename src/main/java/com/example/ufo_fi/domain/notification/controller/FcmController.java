package com.example.ufo_fi.domain.notification.controller;

import com.example.ufo_fi.domain.notification.controller.api.FcmTokenApiSpec;
import com.example.ufo_fi.domain.notification.dto.request.FcmTokenSaveReq;
import com.example.ufo_fi.domain.notification.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.domain.notification.service.FcmService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.Builder.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController implements FcmTokenApiSpec {

    private final FcmService fcmTokenService;

    // PostMapping, 사용자의 FCM 토큰 Save
    @Override
    public ResponseEntity<ResponseBody<FcmTokenCommonRes>> saveToken(
            FcmTokenSaveReq request,
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        fcmTokenService.save(defaultUserPrincipal.getId(), request.getToken())));
    }
}
