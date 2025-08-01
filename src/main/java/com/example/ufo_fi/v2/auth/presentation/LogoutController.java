package com.example.ufo_fi.v2.auth.presentation;

import com.example.ufo_fi.v2.auth.presentation.api.LogoutApiSpec;
import com.example.ufo_fi.v2.auth.application.LogoutService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
