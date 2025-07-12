package com.example.ufo_fi.domain.notification.service;

import com.example.ufo_fi.domain.notification.dto.response.NotificationSettingReadRes;
import com.example.ufo_fi.domain.notification.entity.NotificationSetting;
import com.example.ufo_fi.domain.notification.entity.NotificationType;
import com.example.ufo_fi.domain.notification.exception.NotificationErrorCode;
import com.example.ufo_fi.domain.notification.repository.NotificationRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSettingService {

    private final NotificationRepository notificationRepository;

    /**
     * 알림 설정 목록 조회
     * 1. 기존 설정 조회
     * 2. 판매, 관심상품, 신고 중 하나라도 true 이면 거래 전체 알림 true
     * 3. 설정 목록 반환
     */
    public NotificationSettingReadRes getNotificationSettings(Long userId) {

        NotificationSetting settings = notificationRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(NotificationErrorCode.NO_NOTIFICATION_SETTINGS));

        boolean isTradeAll = settings.getIsSellAgreed()
                || settings.getIsReportedAgreed()
                || settings.getIsInterestedPostAgreed();

        return NotificationSettingReadRes.from(settings, isTradeAll);
    }


    /**
     * 알림 설정 수정
     * 1. 기존 설정 조회
     * 2. 알림 타입에 따라 필드 업데이트
     */
    @Transactional
    public void updateNotificationSettings(Long userId, NotificationType type, boolean enable) {

        NotificationSetting setting = notificationRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(NotificationErrorCode.NO_NOTIFICATION_SETTINGS));
        switch (type) {
            case TRADE -> setting.updateTradeGroup(enable);
            case BENEFIT, SELL, INTERESTED_POST, REPORTED, FOLLOWER_POST -> setting.update(type, enable);
            default -> throw new GlobalException(NotificationErrorCode.INVALID_NOTIFICATION_TYPE);
        }
    }
}
