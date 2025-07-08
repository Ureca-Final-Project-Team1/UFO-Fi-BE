package com.example.ufo_fi.domain.notification.entity;

import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @Column(name = "is_interested_plan_agreed")
    private Boolean isInterestedPlanAgreed;

    @Column(name = "is_reported_agreed")
    private Boolean isReportedAgreed;

    @Column(name = "is_follower_post_agreed")
    private Boolean isFollowerPostAgreed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
