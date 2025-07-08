package com.example.ufo_fi.global.security.refresh.service;

import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.security.jwt.JwtUtil;
import com.example.ufo_fi.global.security.oauth.CookieUtil;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import com.example.ufo_fi.global.security.refresh.RefreshUtil;
import com.example.ufo_fi.global.security.refresh.dto.response.RefreshReissueRes;
import com.example.ufo_fi.global.security.refresh.entity.Refresh;
import com.example.ufo_fi.global.security.refresh.repository.RefreshRepository;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final JwtUtil jwtUtil;
    private final RefreshUtil refreshUtil;
    private final CookieUtil cookieUtil;

    /**
     * 토큰 재발급
     * 1. 리프레시 토큰을 헤더에서 빼온다.
     * 2. 만료된 리프레시 토큰인지, 서명이 옳게 된 리프레시 토큰인지, 그외 타당한 리프레시 토큰인지 검사 => 예외 던짐
     * 3. 리프레시 토큰을 새롭게 갱신 후 헤더에 담아준다.
     * 4. JWT 토큰 또한 헤더에 담아 보내준다.
     *
     * --------------------------------------------------------------------------------
     * 2차 MVP 개선 방안
     * 1. XSS 공부 후 HTTPONLY + COOKIE에 리프레시 토큰을 저장하는 방식
     * 2. STATELESS 방식에서 STATEFUL 방식으로 업그레이드/ 리프레시 토큰 또한 갱신 가능
     */
    public RefreshReissueRes reissueJwt(DefaultUserPrincipal principal, HttpServletRequest request, HttpServletResponse response) {
        String refresh = request.getHeader("refresh-token");
        refreshUtil.validateRefresh(refresh);

        cookieUtil.setResponseBasicCookie(
                "Authorization",
                jwtUtil.generateJwt(principal.getId(), principal.getRole()),
                506000,
                response
        );

        return RefreshReissueRes.from(true);
    }
}
