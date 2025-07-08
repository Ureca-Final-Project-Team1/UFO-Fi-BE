package com.example.ufo_fi.domain.onboard.service;

import com.example.ufo_fi.domain.onboard.dto.request.SignupReq;
import com.example.ufo_fi.domain.onboard.exception.SignupErrorCode;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;

    /**
     * 온보딩 시 유저의 정보를 받는 API
     * 1. user를 찾아온다.
     * 2. user를 업데이트
     */
    @Transactional
    public Void updateUser(Long id, SignupReq signupReq) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GlobalException(SignupErrorCode.NO_USER));

        user.signup(signupReq);
        userRepository.save(user);
        return null;
    }
}
