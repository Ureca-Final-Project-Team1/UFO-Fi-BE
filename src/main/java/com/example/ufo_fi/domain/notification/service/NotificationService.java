package com.example.ufo_fi.domain.notification.service;

import com.example.ufo_fi.domain.notification.dto.response.NotificationListRes;
import com.example.ufo_fi.domain.notification.entity.NotificationHistory;
import com.example.ufo_fi.domain.notification.entity.NotificationType;
import com.example.ufo_fi.domain.notification.repository.NotificationHistoryRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.exception.UserErrorCode;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationHistoryRepository notificationHistoryRepository;
    private final UserRepository userRepository;

    @Value("${firebase.base-url}")
    private String baseUrl;

    /**
     * 최근 발행된 알림 조회
     * 최대 10건
     */
    public NotificationListRes readNotifications(Long userId) {
        List<NotificationHistory> histories = notificationHistoryRepository.findTop10ByUserIdOrderByNotifiedAtDesc(userId);

        return NotificationListRes.from(histories);
    }

    /**
     * 알림 저장
     * 유니캐스트
     */
    @Transactional
    public void saveNotification(Long userId, String title, String body, NotificationType type, String url) {
        // 1. 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        // 2. URL 조립
        String targetUrl = baseUrl + url;

        // 3. 알림 내역 생성
        NotificationHistory history = NotificationHistory.of(user, title, body, type, targetUrl);

        // 4. 저장
        notificationHistoryRepository.save(history);
    }

    /**
     * 알림 저장
     * 멀티캐스트
     */
    @Transactional
    public void saveAllNotification(List<Long> userIds, String title, String body, NotificationType type, String url) {
        // 1. 유저 목록 조회
        List<User> users = userRepository.findAllById(userIds);

        // 2. URL 조립
        String targetUrl = baseUrl + url;

        // 3. 알림 내역 리스트 생성
        List<NotificationHistory> notifications = users.stream()
                .map(user -> NotificationHistory.of(user, title, body, type, targetUrl))
                .toList();

        // 4. 일괄 저장
        notificationHistoryRepository.saveAll(notifications);
    }
}
