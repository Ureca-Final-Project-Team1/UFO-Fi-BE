package com.example.ufo_fi.domain.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmTokenSaveReq {

    @Schema(description = "FCM token")
    private String token;
}
