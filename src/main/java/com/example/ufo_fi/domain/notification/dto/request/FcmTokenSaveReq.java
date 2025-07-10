package com.example.ufo_fi.domain.notification.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmTokenSaveReq {
    private String token;
}
