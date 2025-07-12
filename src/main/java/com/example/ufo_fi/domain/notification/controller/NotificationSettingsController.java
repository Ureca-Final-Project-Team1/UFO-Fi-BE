package com.example.ufo_fi.domain.notification.controller;

import com.example.ufo_fi.domain.notification.controller.api.NotificationSettingsApiSpec;
import com.example.ufo_fi.domain.notification.dto.response.NotificationSettingsReadRes;
import com.example.ufo_fi.domain.notification.entity.NotificationType;
import com.example.ufo_fi.domain.notification.service.NotificationSettingsService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationSettingsController implements NotificationSettingsApiSpec {

    private final NotificationSettingsService notificationService;

    // GetMapping, 알림 설정 목록 Read
    public ResponseEntity<ResponseBody<NotificationSettingsReadRes>> readNotificationSettings(
            @RequestParam Long userId) {
        return ResponseEntity.ok(ResponseBody.success(notificationService.getNotificationSettings(userId)));
    }

    // PatchMapping, 알림 설정 Update
    // TODO 유효하지 않은 알림 들어왔을 경우 예외 처리
    public ResponseEntity<ResponseBody<Void>> updateNotificationSettingsBenefit(
            Long userId,
            NotificationType type,
            boolean enable) {
        notificationService.updateNotificationSettings(userId, type, enable);
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
