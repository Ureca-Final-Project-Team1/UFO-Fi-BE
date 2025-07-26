package com.example.ufo_fi.domain.payment.entity;


import com.example.ufo_fi.domain.payment.dto.request.PaymentReq;
import com.example.ufo_fi.domain.payment.state.State;
import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "method")
    private String method;  // 결제 수단

    @Column(name = "package_name")
    private String packageName;  // 충전 패키지 이름(상품명)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;


    public static Payment of(PaymentReq request, User user) {
        return Payment.builder()
                .userId(user)
                .orderId(request.getOrderId())
                .packageName(request.getPackageName())
                .amount(request.getAmount())
                .status(PaymentStatus.CREATE)
                .requestedAt(LocalDateTime.now())
                .build();
    }

    public void update(String paymentKey, String method, LocalDateTime approvedAt) {
        this.paymentKey = paymentKey;
        this.method = method;
        this.status = PaymentStatus.DONE;
        this.approvedAt = approvedAt;
    }

    public void proceed() {
        getState().proceed(this);
    }

    public State getState() {
        return status.getState();
    }

    public void changeState(PaymentStatus nextStatus) {
        this.status = nextStatus;
    }
}
