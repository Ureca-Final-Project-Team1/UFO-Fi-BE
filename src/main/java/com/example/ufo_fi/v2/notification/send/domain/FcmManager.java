package com.example.ufo_fi.v2.notification.send.domain;

import com.example.ufo_fi.v2.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmManager {
    public FcmToken saveFcmToken(String token, User userProxy) {
        return FcmToken.of(token, userProxy);
    }
}
