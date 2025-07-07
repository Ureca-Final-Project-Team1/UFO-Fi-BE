package com.example.ufo_fi.global.security.refresh.controller;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import com.example.ufo_fi.global.security.refresh.dto.response.RefreshReissueRes;
import com.example.ufo_fi.global.security.refresh.service.RefreshService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshController {
    private final RefreshService refreshService;

    /**
     * 만약, JWT 토큰이 만료되어, API 호출에서 예외를 반환하였다면, 이 API를 호출해, Jwt토큰을 갱신해줘야 한다.
     * response에 새롭게 갱신된 jwt 토큰을 호출해주어야 한다.
     */
    @GetMapping("/refresh")
    public ResponseEntity<ResponseBody<RefreshReissueRes>> refresh(
            @AuthenticationPrincipal DefaultUserPrincipal principal,
            HttpServletRequest request,
            HttpServletResponse response){
        return ResponseEntity.ok(ResponseBody.success(refreshService.reissueJwt(principal, request, response)));
    }
}
