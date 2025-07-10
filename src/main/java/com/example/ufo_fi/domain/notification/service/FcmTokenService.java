package com.example.ufo_fi.domain.notification.service;

import com.example.ufo_fi.domain.notification.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.domain.notification.entity.FcmToken;
import com.example.ufo_fi.domain.notification.repository.FcmTokenRepository;
import com.example.ufo_fi.domain.user.UserRepository;
import com.example.ufo_fi.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;

    /**
     * FCM 토큰을 저장
     * 1. 사용자 ID로 User를 조회하고, 존재하지 않으면 예외를 발생한다.
     * 2. 성공 시, 해당 userId를 응답으로 반환한다.
     */
    @Transactional
    public FcmTokenCommonRes save(Long userId, String token) {

        User user = userRepository.getReferenceById(userId);

        FcmToken fcmToken = FcmToken.of(token, user);
        fcmTokenRepository.save(fcmToken);

        return new FcmTokenCommonRes(userId);
    }
}
