package com.example.ufo_fi.domain.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRes {

    private String receiptUrl;

    public static ConfirmRes from(String receiptUrl) {
        return ConfirmRes.builder()
                .receiptUrl(receiptUrl)
                .build();
    }
}
