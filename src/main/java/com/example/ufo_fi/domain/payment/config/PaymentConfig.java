package com.example.ufo_fi.domain.payment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PaymentConfig {

    @Value("${payment.client-key}")
    private String paymentClientKey;

    @Value("${payment.secrete-key}")
    private String paymentSecreteKey;

    @Value("${payment.confirmUrl}")
    private String confirmUrl;
}
