package com.example.ufo_fi.global.security.config;

import com.example.ufo_fi.domain.user.entity.Role;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.security.jwt.JwtFilter;
import com.example.ufo_fi.global.security.jwt.JwtUtil;
import com.example.ufo_fi.global.security.noinfo.NoInfoRoleFilter;
import com.example.ufo_fi.global.security.oauth.CookieUtil;
import com.example.ufo_fi.global.security.oauth.CustomOAuth2AuthenticationSuccessHandler;
import com.example.ufo_fi.global.security.oauth.CustomOAuth2UserService;
import com.example.ufo_fi.global.security.refresh.RefreshUtil;
import com.example.ufo_fi.global.security.refresh.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@Profile("!test")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${cors.allowed-origin}")
    private String corsOrigin;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RefreshUtil refreshUtil;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http    //rest api 기본 설정 추가
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http    //OAuth2.0 로그인 흐름 설정
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(info -> info.userService(customOAuth2UserService))
                        .successHandler(customOAuth2SuccessHandler()));

        http    //커스텀 필터들 추가
                .addFilterBefore(jwtFilter(), OAuth2AuthorizationRequestRedirectFilter.class);

        http    //인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/refresh").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v1/signup").hasAuthority(Role.ROLE_NO_INFO.toString())
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter(jwtUtil, cookieUtil);
    }

    @Bean
    public NoInfoRoleFilter noInfoRoleFilter(){
        return new NoInfoRoleFilter();
    }

    @Bean
    public CustomOAuth2AuthenticationSuccessHandler customOAuth2SuccessHandler() {
        return new CustomOAuth2AuthenticationSuccessHandler(
                jwtUtil,
                cookieUtil,
                refreshUtil,
                userRepository,
                refreshRepository
        );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration(); // cors 설정
        configuration.addAllowedOrigin(corsOrigin); // 허용할 origin 설정
        configuration.addAllowedMethod("*"); // 모든 HTTP 메소드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 위 설정 적용
        return source;
    }
}
