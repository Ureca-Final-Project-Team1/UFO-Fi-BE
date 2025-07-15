package com.example.ufo_fi.domain.notification.processor;

import com.example.ufo_fi.domain.notification.entity.NotificationType;
import com.example.ufo_fi.domain.notification.event.AccountSuspendEvent;
import com.example.ufo_fi.domain.notification.event.NotificationTemplate;
import com.example.ufo_fi.domain.notification.service.FcmService;
import com.example.ufo_fi.domain.notification.service.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 신고 누적 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class AccountSuspendNotificationProcessor {
    private final NotificationSettingService notificationSettingService;
    private final FcmService fcmService;

    public void process(AccountSuspendEvent event) {
        Long userId = event.getUserId();

        // 1. 알림 설정 확인
        if (!notificationSettingService.isEnabled(userId, NotificationType.REPORTED)) return;

        // 2. 메시지 조립 (템플릿)
        NotificationTemplate template = NotificationTemplate.USER_BLOCK;
        String title = template.getTitle();
        String body = template.getBody();

        // 3. 전송
        fcmService.sendUnicastByUserId(userId, title, body);
    }
}
