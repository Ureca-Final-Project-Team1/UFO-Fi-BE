package com.example.ufo_fi.domain.notification.repository;

import com.example.ufo_fi.domain.notification.entity.NotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
    Optional<NotificationSetting> findByUserId(Long userId);

    // 혜택 알림 수신을 동의한 모든 사용자
    @Query("SELECT ns.user.id FROM NotificationSetting ns WHERE ns.isEventAgreed = true")
    List<Long> findUserIdsWithBenefitAgreed();
}
