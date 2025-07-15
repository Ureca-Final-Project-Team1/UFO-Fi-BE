package com.example.ufo_fi.domain.notification.service;

import com.example.ufo_fi.domain.notification.dto.response.NotificationSettingReadRes;
import com.example.ufo_fi.domain.notification.entity.NotificationSetting;
import com.example.ufo_fi.domain.notification.entity.NotificationType;
import com.example.ufo_fi.domain.notification.exception.NotificationErrorCode;
import com.example.ufo_fi.domain.notification.repository.NotificationSettingRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSettingService {

    private final NotificationSettingRepository notificationSettingRepository;

    /**
     * 알림 설정 목록 조회
     * 1. 기존 설정 조회
     * 2. 판매, 관심상품, 신고 중 하나라도 true 이면 거래 전체 알림 true
     * 3. 설정 목록 반환
     */
    public NotificationSettingReadRes getNotificationSettings(Long userId) {

        NotificationSetting settings = notificationSettingRepository.findByUserId(userId)
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

        NotificationSetting setting = notificationSettingRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(NotificationErrorCode.NO_NOTIFICATION_SETTINGS));
        switch (type) {
            case TRADE -> setting.updateTradeGroup(enable);
            case BENEFIT, SELL, INTERESTED_POST, REPORTED, FOLLOWER_POST -> setting.update(type, enable);
            default -> throw new GlobalException(NotificationErrorCode.INVALID_NOTIFICATION_TYPE);
        }
    }

    /**
     * 알림 설정 조회: 단일 유저용 (유니캐스트용)
     */
    public boolean isEnabled(Long userId, NotificationType type) {
        NotificationSetting setting = notificationSettingRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(NotificationErrorCode.NO_NOTIFICATION_SETTINGS));
        return resolve(type, setting);
    }

    /**
     * 알림 설정 조회 시, 각각의 알림 분기 로직
     * TODO: 분기 처리 로직 리팩토링
     */
    private boolean resolve(NotificationType type, NotificationSetting setting) {
        return switch (type) {
            case SELL -> setting.getIsSellAgreed();
            case REPORTED -> setting.getIsReportedAgreed();
            default -> false;
        };
    }
}
