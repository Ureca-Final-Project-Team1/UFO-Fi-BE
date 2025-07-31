package com.example.ufo_fi.v2.notification.send.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.notification.send.application.FcmService;
import com.example.ufo_fi.v2.notification.send.presentation.api.FcmTokenApiSpec;
import com.example.ufo_fi.v2.notification.send.presentation.dto.request.FcmTokenSaveReq;
import com.example.ufo_fi.v2.notification.send.presentation.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController implements FcmTokenApiSpec {

    private final FcmService fcmTokenService;

    @Override
    public ResponseEntity<ResponseBody<FcmTokenCommonRes>> saveToken(
            FcmTokenSaveReq request,
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        fcmTokenService.saveFcmToken(defaultUserPrincipal.getId(), request.getToken())));
    }
}
