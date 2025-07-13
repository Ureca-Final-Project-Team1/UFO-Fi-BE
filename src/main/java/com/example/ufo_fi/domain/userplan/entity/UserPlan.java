package com.example.ufo_fi.domain.userplan.entity;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.signup.dto.request.SignupReq;
import com.example.ufo_fi.domain.signup.dto.request.UserPlanReq;
import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "carrier")
    private Carrier carrier;

    @Column(name = "plan_name", length = 255)
    private String planName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_data_type")
    private MobileDataType mobileDataType;

    @Column(name = "sell_mobile_data_capacity_gb")
    private int sellMobileDataCapacityGb;

    @Column(name = "sellable_data_amount")
    private Integer sellableDataAmount;

    public void subtractSellableDataAmount(int requestSellData) {

        if (requestSellData < 0 || requestSellData > this.sellableDataAmount) {
            
            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        this.sellableDataAmount -= requestSellData;
    }

    public void increaseSellableDataAmount(int restore) {

        if (restore < 0 || restore + this.sellableDataAmount > sellMobileDataCapacityGb) {

            throw new GlobalException(TradePostErrorCode.EXCEED_RESTORE_CAPACITY);
        }

        this.sellableDataAmount += restore;
    }

    public void update(Plan plan) {
        this.carrier = plan.getCarrier();
        this.planName = plan.getName();
        this.mobileDataType = plan.getMobileDataType();
        this.sellMobileDataCapacityGb = plan.getSellMobileDataCapacityGb();
        this.sellableDataAmount = plan.getSellMobileDataCapacityGb();
    }

    public static UserPlan from(UserPlanReq userPlanReq){
        return UserPlan.builder()
                .carrier(userPlanReq.getCarrier())
                .planName(userPlanReq.getPlanName())
                .mobileDataType(userPlanReq.getMobileDataType())
                .sellMobileDataCapacityGb(userPlanReq.getSellMobileDataCapacityGB())
                .sellableDataAmount(userPlanReq.getSellMobileDataCapacityGB())
                .build();
    }
}
