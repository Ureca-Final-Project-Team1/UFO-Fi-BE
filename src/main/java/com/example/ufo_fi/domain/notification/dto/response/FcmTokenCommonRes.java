package com.example.ufo_fi.domain.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FcmTokenCommonRes(
        @Schema(description = "나의 FCM 토큰 식별번호")
        Long id

) {
}
