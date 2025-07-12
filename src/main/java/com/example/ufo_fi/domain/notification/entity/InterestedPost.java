package com.example.ufo_fi.domain.notification.entity;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interested_post")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestedPost {
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
