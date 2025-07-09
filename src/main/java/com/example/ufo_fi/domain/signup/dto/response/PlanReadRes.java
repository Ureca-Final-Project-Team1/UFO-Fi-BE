package com.example.ufo_fi.domain.signup.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanReadRes {
    private String planName;
    private Carrier carrier;
    private MobileDataType mobileDataAmount;
    private Boolean isUltimatedAmount;
    private Integer sellMobileDataCapacityGB;
    private MobileDataType mobileDataType;

    public static PlanReadRes from(Plan plan) {
        return PlanReadRes.builder()
                .planName(plan.getName())
                .carrier(plan.getCarrier())
                .mobileDataAmount(plan.getMobileDataType())
                .isUltimatedAmount(plan.getIsUltimatedAmount())
                .sellMobileDataCapacityGB(plan.getSellMobileDataCapacityGb())
                .mobileDataAmount(plan.getMobileDataType())
                .build();
    }
}
