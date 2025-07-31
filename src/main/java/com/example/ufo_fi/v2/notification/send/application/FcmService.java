package com.example.ufo_fi.v2.notification.send.application;

import com.example.ufo_fi.v2.notification.send.domain.FcmManager;
import com.example.ufo_fi.v2.notification.send.domain.FcmToken;
import com.example.ufo_fi.v2.notification.send.presentation.dto.response.FcmTokenCommonRes;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final FcmManager fcmManager;
    private final FcmMapper fcmMapper;
    private final EntityManager entityManager;

    /**
     * FCM 토큰을 저장
     * 1. 사용자 ID로 User를 조회하고, 존재하지 않으면 예외를 발생한다.
     * 2. 성공 시, 해당 userId를 응답으로 반환한다.
     */
    @Transactional
    public FcmTokenCommonRes saveFcmToken(Long userId, String token) {

        User userProxy = entityManager.getReference(User.class, userId);
        FcmToken fcmToken = fcmManager.saveFcmToken(token, userProxy);

        return fcmMapper.toFcmTokenCommonRes(fcmToken);
        // return new FcmTokenCommonRes(userId);
    }

//    /**
//     * 멀티 캐스트 (다수 유저용)
//     */
//    public void sendMulticastByUserIds(List<Long> userIds, String title, String body, String url) {
//        List<String> tokens = fcmTokenRepository.findTokensByUserIds(userIds);
//        if (tokens.isEmpty()) return;
//
//        MulticastMessage message = MulticastMessage.builder()
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(body)
//                        .build())
//                .addAllTokens(tokens)
//                .putData("url", baseUrl + url)
//                .build();
//
//        try {
//            firebaseMessaging.sendEachForMulticast(message);
//
//        } catch (FirebaseMessagingException e) {
//            throw new GlobalException(NotificationErrorCode.MESSAGE_SEND_FAILED);
//        }
//    }
//
//    /**
//     * 유니 캐스트 (단일 유저용)
//     */
//    public void sendUnicastByUserId(Long userId, String title, String body, String url) {
//        Optional<String> tokenOpt = fcmTokenRepository.findByUserId(userId);
//        tokenOpt.ifPresent(token -> {
//            Message message = Message.builder()
//                    .setNotification(Notification.builder()
//                            .setTitle(title)
//                            .setBody(body)
//                            .build())
//                    .putData("url", baseUrl + url)
//                    .setToken(token)
//                    .build();
//
//            try {
//                firebaseMessaging.send(message);
//            } catch (FirebaseMessagingException e) {
//                throw new GlobalException(NotificationErrorCode.MESSAGE_SEND_FAILED);
//            }
//        });
//    }
}
