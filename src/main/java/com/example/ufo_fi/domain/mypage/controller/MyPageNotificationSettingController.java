package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPageNotificationSettingApiSpec;
import com.example.ufo_fi.domain.notification.dto.response.NotificationSettingReadRes;
import com.example.ufo_fi.domain.notification.entity.NotificationType;
import com.example.ufo_fi.domain.notification.service.NotificationSettingService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageNotificationSettingController implements MyPageNotificationSettingApiSpec {

    private final NotificationSettingService notificationService;

    // GetMapping, 알림 설정 목록 Read
    @Override
    public ResponseEntity<ResponseBody<NotificationSettingReadRes>> readNotificationSettings(
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        notificationService.getNotificationSettings(defaultUserPrincipal.getId())));
    }

    // PatchMapping, 알림 설정 Update
    // TODO 유효하지 않은 알림 들어왔을 경우 예외 처리
    @Override
    public ResponseEntity<ResponseBody<Void>> updateNotificationSettings(
            DefaultUserPrincipal defaultUserPrincipal,
            NotificationType type,
            boolean enable
    ) {
        notificationService.updateNotificationSettings(defaultUserPrincipal.getId(), type, enable);
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
