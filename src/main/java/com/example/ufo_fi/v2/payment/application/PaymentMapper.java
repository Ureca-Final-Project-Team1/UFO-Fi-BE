package com.example.ufo_fi.v2.payment.application;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ZetRecoveryRes;
import com.example.ufo_fi.v2.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public ZetRecoveryRes toZetRecoveryRes(User user, PaymentStatus status) {
        return ZetRecoveryRes.builder()
                .userId(user.getId())
                .zet(user.getZetAsset())
                .paymentStatus(status)
                .build();
    }
}
