package com.example.ufo_fi.global.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * ResponseEntity는 Spring MVC에서 사용된다.
 * 하지만, 시큐리티는 필터 단의 로직으로 더 앞의 순서에 있다.
 * 때문에, 직접 HttpServletResponse에 응답값을 세팅을 해줘야 한다.
 * 인증 단계에서 발생하는 예외를 처리하는 핸들러이다.
 * 인증 단계에서는 AuthenticationException을 상속받은 예외들이 throw 된다.
 */
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final SecurityExceptionResponseSetter exceptionResponseSetter;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        exceptionResponseSetter.setResponse(response, HttpStatus.UNAUTHORIZED, authException);
    }
}
