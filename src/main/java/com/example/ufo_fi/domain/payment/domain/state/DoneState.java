package com.example.ufo_fi.domain.payment.domain.state;

import com.example.ufo_fi.domain.payment.domain.Payment;
import com.example.ufo_fi.domain.payment.domain.PaymentStatus;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DoneState implements State<Void> {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void proceed(Payment payment, Void unused) {
        User user = payment.getUser();
        entityManager.merge(user);
        user.increaseZetAsset(payment.getAmount());
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.DONE;
    }
}
