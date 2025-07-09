package com.example.ufo_fi.domain.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Firebase 설정 클래스
 * 1. 설정 파일 경로가 주어졌을 경우에만 FirebaseApp을 초기화한다.
 * 2. 이미 초기화된 FirebaseApp이 있다면 기존 인스턴스를 반환한다.
 * 3. FirebaseApp이 정상적으로 초기화된 경우에만 FirebaseMessaging Bean을 등록한다.
 * 그렇지 않으면 null을 반환하여 초기화를 회피하도록 한다.
 */
@Configuration
public class FirebaseConfig {

    @Value("${firebase.config-path:}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseApp firebaseApp() {
        if (firebaseConfigPath == null || firebaseConfigPath.isBlank()) {
            return null;
        }

        try (InputStream serviceAccount = new ClassPathResource(firebaseConfigPath.trim()).getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp app = FirebaseApp.initializeApp(options);
                return app;
            } else {
                return FirebaseApp.getInstance();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        if (firebaseApp == null) {
            return null;
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
