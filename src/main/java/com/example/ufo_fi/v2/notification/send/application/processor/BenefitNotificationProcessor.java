package com.example.ufo_fi.v2.notification.send.application.processor;

import com.example.ufo_fi.v2.notification.common.NotificationMessage;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.history.applicaton.NotificationHistoryService;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.event.BenefitEvent;
import com.example.ufo_fi.v2.notification.send.domain.event.NotificationTemplate;
import com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request.PushMassageCommand;
import com.example.ufo_fi.v2.notification.setting.persistence.NotificationSettingRepository;
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
    private final NotificationHistoryService notificationHistoryService;

    private final WebPushClient webPushClient;
    private final FcmManager fcmManager;

    public void process(BenefitEvent event) {

        // 1. 혜택 알림 수신 동의한 사용자 조회
        List<Long> enabledUserIds = notificationSettingRepository.findUserIdsWithBenefitAgreed();

        // 2. 메시지 조립 (템플릿)
        NotificationTemplate template = NotificationTemplate.EVENT_BENEFIT;
        String title = template.getTitle();
        String body = template.getBody();
        String url = template.getUrl();

        NotificationMessage message = NotificationMessage.from(enabledUserIds, title, body, NotificationType.BENEFIT, url);

        // 4. 전송
        List<String> tokens = fcmManager.readFcmTokens(enabledUserIds);
        PushMassageCommand pushMassageCommand = PushMassageCommand.from(tokens, message);
        webPushClient.sendMulticast(pushMassageCommand);
        notificationHistoryService.saveAllNotification(message);
    }
}
