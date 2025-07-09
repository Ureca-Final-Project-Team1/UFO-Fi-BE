package com.example.ufo_fi.domain.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FirebaseInitializer {

    @Value("${firebase.config-path}")
    private String firebaseConfigPath;

    /**
     * FCM APP 초기화
     * 1. Spring Boot 애플리케이션이 시작될 때 ApplicationReadyEvent를 통해 초기화를 시도한다.
     * 2. Firebase 비공개 키(JSON)를 사용해 인증 후 FCM 앱을 초기화한다.
     * 3.이미 FirebaseApp이 초기화되어 있으면 중복 초기화를 방지하고 건너뛴다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initFirebase() {

        try {
            InputStream serviceAccount = new ClassPathResource(firebaseConfigPath.replace("classpath:", "").trim()).getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
