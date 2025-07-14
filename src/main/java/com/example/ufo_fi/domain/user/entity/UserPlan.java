package com.example.ufo_fi.domain.user.entity;

import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.user.dto.request.UserPlanReq;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public static UserPlan from(final Plan plan) {
        return UserPlan.builder()
                .sellableDataAmount(plan.getSellMobileDataCapacityGb())
                .plan(plan)
                .build();
    }

    public void subtractSellableDataAmount(int requestSellData) {

        if (requestSellData < 0 || requestSellData > this.sellableDataAmount) {
            
            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        this.sellableDataAmount -= requestSellData;
    }

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
