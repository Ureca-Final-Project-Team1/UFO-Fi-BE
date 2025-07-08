package com.example.ufo_fi.domain.userplan.entity;

import com.example.ufo_fi.domain.onboard.dto.request.UserPlanCreateReq;
import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_plans")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier", nullable = false)
    private Carrier carrier;

    @Column(name = "plan_name", nullable = false, length = 255)
    private String planName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_data_type", nullable = false)
    private MobileDataType mobileDataType;

    @Column(name = "sell_mobile_data_capacity_gb")
    private Integer sellMobileDataCapacityGb;

    @Column(name = "sellable_data_amount")
    private Integer sellableDataAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    public static UserPlan of(UserPlanCreateReq userPlanCreateReq, User user){
        return UserPlan.builder()
                .carrier(userPlanCreateReq.getCarrier())
                .planName(userPlanCreateReq.getPlanName())
                .mobileDataType(userPlanCreateReq.getMobileDataType())
                .sellMobileDataCapacityGb(userPlanCreateReq.getSellMobileDataCapacityGb())
                .sellableDataAmount(userPlanCreateReq.getSellMobileDataCapacityGb())    //초기 세팅은 sellMobileDataCapacityGB와 같다.
                .user(user)
                .build();
    }
}
