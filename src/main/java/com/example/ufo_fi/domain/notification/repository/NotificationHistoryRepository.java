package com.example.ufo_fi.domain.notification.repository;

import com.example.ufo_fi.domain.notification.entity.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    List<NotificationHistory> findTop10ByUserIdOrderByNotifiedAtDesc(Long userId);
}
