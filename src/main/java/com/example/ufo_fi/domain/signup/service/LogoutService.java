package com.example.ufo_fi.domain.signup.service;

import com.example.ufo_fi.v2.auth.application.jwt.JwtUtil;
import com.example.ufo_fi.v2.auth.persistence.RefreshRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public void logout(HttpServletRequest request, HttpServletResponse response, Long userId){
        // 1. 응답으로 만료시간이 다 된 jwt쿠키를 추가하여, 쿠키를 삭제한다.
        jwtUtil.deleteJwtCookie(response);

        // 2. Refresh Token 제거
        refreshRepository.deleteById(userId);
    }
}
