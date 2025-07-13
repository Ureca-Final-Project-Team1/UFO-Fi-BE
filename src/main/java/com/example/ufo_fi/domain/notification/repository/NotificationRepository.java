package com.example.ufo_fi.domain.notification.repository;

import com.example.ufo_fi.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
