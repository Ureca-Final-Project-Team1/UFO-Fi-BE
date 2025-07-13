package com.example.ufo_fi.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    // 알림 보내보기, test만 완료, 미완성 메서드
    public void sendMessage(String targetToken, String title, String body) {

    }
}
