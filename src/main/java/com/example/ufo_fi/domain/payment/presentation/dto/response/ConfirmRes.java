package com.example.ufo_fi.domain.payment.presentation.dto.response;

import com.example.ufo_fi.domain.payment.domain.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRes {

    @Schema(description = "충전된 데이터량입니다.")
    private Integer amount;

    public static ConfirmRes from(Payment payment) {
        return ConfirmRes.builder()
                .amount(payment.getAmount())
                .build();
    }
}
