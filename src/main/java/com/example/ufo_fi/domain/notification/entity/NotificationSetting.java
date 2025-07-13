package com.example.ufo_fi.domain.notification.entity;

import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "notification_settings")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ColumnDefault("false")
    @Column(name = "is_event_agreed")
    private Boolean isEventAgreed;

    @ColumnDefault("false")
    @Column(name = "is_sell_agreed")
    private Boolean isSellAgreed;

    @ColumnDefault("false")
    @Column(name = "is_interested_post_agreed")
    private Boolean isInterestedPostAgreed;

    @ColumnDefault("false")
    @Column(name = "is_reported_agreed")
    private Boolean isReportedAgreed;

    @ColumnDefault("false")
    @Column(name = "is_follower_post_agreed")
    private Boolean isFollowerPostAgreed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static NotificationSetting from(User user) {
        return NotificationSetting.builder()
                .user(user)
                .build();
    }

    /**
     * 거래 알림 전체 설정 업데이트
     */
    public void updateTradeGroup(boolean enabled) {
        this.isSellAgreed = enabled;
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
        }
    }
}
