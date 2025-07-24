package com.example.ufo_fi.domain.user.entity;

import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.persistence.*;
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

    @Column(name = "sellable_data_amount")
    private Integer sellableDataAmount;

    @Column(name = "purchase_data_amount")
    private Integer purchaseDataAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public static UserPlan from(final Plan plan) {
        return UserPlan.builder()
                .sellableDataAmount(plan.getSellMobileDataCapacityGb())
                .purchaseDataAmount(0)
                .plan(plan)
                .build();
    }

    public static UserPlan of(Plan plan, User user) {
        return UserPlan.builder()
                .sellableDataAmount(plan.getSellMobileDataCapacityGb())
                .purchaseDataAmount(0)
                .user(user)
                .plan(plan)
                .build();
    }

    public void subtractSellableDataAmount(int requestSellData) {

        if (requestSellData < 0 || requestSellData > this.sellableDataAmount) {

            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        this.sellableDataAmount -= requestSellData;
    }

    // TODO: 판매가능량과 구매량 분리 필요
    public void increaseSellableDataAmount(int restore) {

        if (restore < 0 || restore + this.sellableDataAmount > plan.getSellMobileDataCapacityGb()) {

            throw new GlobalException(TradePostErrorCode.EXCEED_RESTORE_CAPACITY);
        }

        this.sellableDataAmount += restore;
    }

    public void update(Plan plan) {
        this.plan = plan;
        this.sellableDataAmount = plan.getSellMobileDataCapacityGb();
    }
}
