package com.example.ufo_fi.domain.user.service;

import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;

    public User validateUserExistence(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
    }


}
