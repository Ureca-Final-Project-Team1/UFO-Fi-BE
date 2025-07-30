package com.example.ufo_fi.domain.notification.controller;

import com.example.ufo_fi.domain.notification.controller.api.NotificationApiSpec;
import com.example.ufo_fi.domain.notification.dto.response.NotificationListRes;
import com.example.ufo_fi.domain.notification.service.NotificationService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApiSpec {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<ResponseBody<NotificationListRes>> readNotifications(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(ResponseBody.success(notificationService.readNotifications(defaultUserPrincipal.getId())));
    }
}
