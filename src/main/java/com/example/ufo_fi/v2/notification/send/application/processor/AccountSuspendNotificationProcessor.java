package com.example.ufo_fi.v2.notification.send.application.processor;

import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.history.applicaton.NotificationHistoryService;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.event.AccountSuspendEvent;
import com.example.ufo_fi.v2.notification.send.domain.event.NotificationTemplate;
import com.example.ufo_fi.v2.notification.setting.application.NotificationSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 신고 누적 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class AccountSuspendNotificationProcessor {
    private final NotificationSettingService notificationSettingService;
    private final NotificationHistoryService notificationService;

    private final WebPushClient webPushClient;
    private final FcmManager fcmManager;

    public void process(AccountSuspendEvent event) {
        Long userId = event.getUserId();

        // 1. 알림 설정 확인
        if (!notificationSettingService.isEnabled(userId, NotificationType.REPORTED)) return;

        // 2. 메시지 조립 (템플릿)
        NotificationTemplate template = NotificationTemplate.USER_BLOCK;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        NotificationMessage message = NotificationMessage.from(List.of(userId), title, body, NotificationType.REPORTED, url);

        // 3. 전송
        fcmManager.readFcmTokens(List.of(userId));
        webPushClient.sendUnicast(message);
        notificationService.saveNotification(message);
    }
}
