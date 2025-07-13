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
public class FcmTokenController implements FcmControllerApiSpec{

    private final FcmTokenService fcmTokenService;

    @Override
    public ResponseEntity<ResponseBody<FcmTokenCommonRes>> saveToken(@RequestBody FcmTokenSaveReq request) {
        Long userId = 1L;

        return ResponseEntity.ok(ResponseBody.success(fcmTokenService.save(userId, request.getToken())));
    }
}
