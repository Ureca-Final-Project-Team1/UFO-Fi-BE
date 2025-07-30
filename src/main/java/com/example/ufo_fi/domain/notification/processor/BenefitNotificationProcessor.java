package com.example.ufo_fi.domain.notification.processor;

import com.example.ufo_fi.domain.notification.entity.NotificationType;
import com.example.ufo_fi.domain.notification.event.BenefitEvent;
import com.example.ufo_fi.domain.notification.event.NotificationTemplate;
import com.example.ufo_fi.domain.notification.repository.NotificationSettingRepository;
import com.example.ufo_fi.domain.notification.service.FcmService;
import com.example.ufo_fi.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 혜택 알림 프로세서
 */
@Component
@RequiredArgsConstructor
public class BenefitNotificationProcessor {
    private final NotificationSettingRepository notificationSettingRepository;
    private final FcmService fcmService;
    private final NotificationService notificationService;

    public void process(BenefitEvent event) {

        // 1. 혜택 알림 수신 동의한 사용자 조회
        List<Long> enabledUserIds = notificationSettingRepository.findUserIdsWithBenefitAgreed();

        // 2. 메시지 조립 (템플릿)
        NotificationTemplate template = NotificationTemplate.EVENT_BENEFIT;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        // 4. 전송
        fcmService.sendMulticastByUserIds(enabledUserIds, title, body, url);
        notificationService.saveAllNotification(enabledUserIds, title, body, NotificationType.BENEFIT, url);
    }
}
