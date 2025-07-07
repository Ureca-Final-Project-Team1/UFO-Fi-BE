package com.example.ufo_fi.global.security.refresh;

import com.example.ufo_fi.domain.user.entity.Role;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.global.security.principal.PrincipalKey;
import com.example.ufo_fi.global.security.refresh.exception.RefreshErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JJWT를 사용한 RefreshUtil 구현
 * 모든 필드들, 런타임 시점 초기화 즉 싱글톤 @Component
 */
@Component
@RequiredArgsConstructor
public class RefreshUtil {

    // Base64 인코딩된 256비트 시크릿 키 (application.yml에 설정)
    @Value("${jwt.secret}")
    private String secret;

    // 리프레시 토큰 유효기간 (밀리초)
    @Value("${jwt.refresh-token-validity-ms}")
    private long refreshTokenValidityMs;

    private SecretKey key;

    /**
     * 빈 초기화 시점에 시크릿 키로 SecretKey 객체 생성
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //리프레시 토큰 생성
    public String generateRefresh(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidityMs);

        return Jwts.builder()
                .claim(PrincipalKey.USER_ID.getKey(), userId)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public void validateRefresh(String refresh){
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(refresh)
                    .getPayload();
        } catch (ExpiredJwtException e){
            throw new GlobalException(RefreshErrorCode.REFRESH_EXPIRED);
        } catch (SignatureException e){
            throw new GlobalException(RefreshErrorCode.REFRESH_UNSAFE);
        } catch (Exception e){
            throw new GlobalException(RefreshErrorCode.REFRESH_EXCEPTION);
        }
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}