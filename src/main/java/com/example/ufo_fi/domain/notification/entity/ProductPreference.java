package com.example.ufo_fi.domain.notification.entity;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_preferences")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier")
    private Carrier carrier;

    @Column(name = "interested_max_capacity")
    private Integer interestedMaxCapacity;

    @Column(name = "interested_min_capacity")
    private Integer interestedMinCapacity;

    @Column(name = "interested_max_price")
    private Integer interestedMaxPrice;

    @Column(name = "interested_min_price")
    private Integer interestedMinPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "reputation")
    private Reputation reputation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
