package com.example.ufo_fi.v2.notification.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.notification.application.FcmService;
import com.example.ufo_fi.v2.notification.presentation.api.FcmTokenApiSpec;
import com.example.ufo_fi.v2.notification.presentation.dto.request.FcmTokenSaveReq;
import com.example.ufo_fi.v2.notification.presentation.dto.response.FcmTokenCommonRes;
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
