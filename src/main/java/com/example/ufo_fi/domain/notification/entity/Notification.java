package com.example.ufo_fi.domain.notification.entity;

import com.example.ufo_fi.domain.notification.exception.NotificationErrorCode;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_event_agreed")
    private Boolean isEventAgreed;

    @Column(name = "is_sell_agreed")
    private Boolean isSellAgreed;

    @Column(name = "is_purchase_agreed")
    private Boolean isPurchaseAgreed;

    @Column(name = "is_interested_post_agreed")
    private Boolean isInterestedPostAgreed;

    @Column(name = "is_reported_agreed")
    private Boolean isReportedAgreed;

    @Column(name = "is_follower_post_agreed")
    private Boolean isFollowerPostAgreed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 거래 알림 전체 설정 업데이트
     */
    public void updateTradeGroup(boolean enabled) {
        this.isSellAgreed = enabled;
        this.isPurchaseAgreed = enabled;
        this.isInterestedPostAgreed = enabled;
        this.isReportedAgreed = enabled;
        this.isFollowerPostAgreed = enabled;
    }

    /**
     * 개별 알림 설정 업데이트
     */
    public void update(NotificationType type, boolean enabled) {
        switch (type) {
            case BENEFIT -> this.isEventAgreed = enabled;
            case SELL -> this.isSellAgreed = enabled;
            case INTERESTED_POST -> this.isInterestedPostAgreed = enabled;
            case REPORTED -> this.isReportedAgreed = enabled;
            case FOLLOWER_POST -> this.isFollowerPostAgreed = enabled;
            default -> throw new GlobalException(NotificationErrorCode.INVALID_NOTIFICATION_TYPE);
        }
    }
}
