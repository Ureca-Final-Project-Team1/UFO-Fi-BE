package com.example.ufo_fi.v2.payment.persistence.mysql;

import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
}
