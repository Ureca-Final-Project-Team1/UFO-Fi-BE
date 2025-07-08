package com.example.ufo_fi.global.security.noinfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class NoInfoRoleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isAuthenticated(authentication)) {
            boolean hasRole = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_NO_INFO"));

            String currentPath = request.getRequestURI();

            /**
             * To do
             * => 만일, ROLE_NO_INFO가 온보딩 페이지로 요청을 보내지 않는다면, response에 특정 값 넣어 보내주기
             * => 프론트 측이 그걸 보고 해당 페이지로 보내줌
             */
//            if (hasRole && !currentPath.startsWith("/special-area")) {
//                response.sendRedirect("/special-area");
//                return; // 필터 체인 더 이상 진행 안 함
//            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
}
