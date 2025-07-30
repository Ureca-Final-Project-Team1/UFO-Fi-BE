package com.example.ufo_fi.domain.signup.controller;

import com.example.ufo_fi.domain.signup.controller.api.LogoutApiSpec;
import com.example.ufo_fi.domain.signup.service.LogoutService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutController implements LogoutApiSpec {
    private final LogoutService logoutService;

    @Override
    public ResponseEntity<ResponseBody<Void>> logout(
        DefaultUserPrincipal defaultUserPrincipal,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        logoutService.logout(request, response, defaultUserPrincipal.getId());
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
