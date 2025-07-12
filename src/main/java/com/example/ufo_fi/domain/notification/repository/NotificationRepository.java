package com.example.ufo_fi.domain.notification.repository;

import com.example.ufo_fi.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByUserId(Long userId);
}
