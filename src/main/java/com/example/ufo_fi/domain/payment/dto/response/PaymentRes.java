package com.example.ufo_fi.domain.payment.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRes {

    private String name;
    private String email;
    private String orderId;
    private Integer amount;

    public static PaymentRes from(User user, String orderId, Integer amount) {
        return PaymentRes.builder()
                .name(user.getName())
                .email(user.getEmail())
                .orderId(orderId)
                .amount(amount)
                .build();
    }
}
