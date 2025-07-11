package com.example.ufo_fi.domain.notification.repository;

import com.example.ufo_fi.domain.notification.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
}
