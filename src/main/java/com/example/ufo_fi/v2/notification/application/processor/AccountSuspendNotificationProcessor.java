package com.example.ufo_fi.v2.notification.application.processor;

import com.example.ufo_fi.v2.notification.application.FcmService;
import com.example.ufo_fi.v2.notification.application.NotificationService;
import com.example.ufo_fi.v2.notification.application.NotificationSettingService;
import com.example.ufo_fi.v2.notification.domain.NotificationType;
import com.example.ufo_fi.v2.notification.domain.event.AccountSuspendEvent;
import com.example.ufo_fi.v2.notification.domain.event.NotificationTemplate;
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
    private final NotificationService notificationService;

    public void process(AccountSuspendEvent event) {
        Long userId = event.getUserId();

        // 1. 알림 설정 확인
        if (!notificationSettingService.isEnabled(userId, NotificationType.REPORTED)) return;

        // 2. 메시지 조립 (템플릿)
        NotificationTemplate template = NotificationTemplate.USER_BLOCK;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        // 3. 전송
        fcmService.sendUnicastByUserId(userId, title, body, url);
        notificationService.saveNotification(userId, title, body, NotificationType.REPORTED, url);
    }
}
