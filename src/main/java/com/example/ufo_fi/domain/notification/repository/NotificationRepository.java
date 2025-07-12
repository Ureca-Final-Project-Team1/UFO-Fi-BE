package com.example.ufo_fi.domain.notification.repository;

import com.example.ufo_fi.domain.notification.entity.NotificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationSettings, Long> {
    Optional<NotificationSettings> findByUserId(Long userId);
}
