package com.example.ufo_fi.v2.notification.send.application.processor;

import com.example.ufo_fi.v2.notification.history.applicaton.NotificationService;
import com.example.ufo_fi.v2.notification.send.application.FcmService;
import com.example.ufo_fi.v2.notification.send.domain.event.NotificationTemplate;
import com.example.ufo_fi.v2.notification.send.domain.event.TradeCompletedEvent;
import com.example.ufo_fi.v2.notification.setting.application.NotificationSettingService;
import com.example.ufo_fi.v2.notification.setting.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 거래 완료 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class TradeCompletedNotificationProcessor {

    private final NotificationSettingService notificationSettingService;
    private final FcmService fcmService;
    private final NotificationService notificationService;

    public void process(TradeCompletedEvent event) {
        Long userId = event.getSellerId();

        // 1. 알림 설정 확인
        if (!notificationSettingService.isEnabled(userId, NotificationType.SELL)) return;

        // 2. 메시지 조립 (템플릿)
        NotificationTemplate template = NotificationTemplate.DATA_SELL;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        // 3. 전송
        fcmService.sendUnicastByUserId(userId, title, body, url);
        notificationService.saveNotification(userId, title, body, NotificationType.SELL, url);
    }
}
