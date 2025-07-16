package com.example.ufo_fi.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Security 추가 시 삭제 예정
 * 누군가 발견 시 정파트장한테 말해줄 것.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // TODO: 추후 Security 이동 시 url 환경 변수 처리
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://ufo-fi.store")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false); // ← withCredentials: false인 경우
    }
}
