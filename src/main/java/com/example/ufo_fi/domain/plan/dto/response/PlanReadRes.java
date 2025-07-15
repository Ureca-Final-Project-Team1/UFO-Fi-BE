package com.example.ufo_fi.domain.plan.dto.response;

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
    private Integer mobileDataAmount;
    private Boolean isUltimatedAmount;
    private Integer sellMobileDataCapacityGB;
    private MobileDataType mobileDataType;

    public static PlanReadRes from(final Plan plan) {
        return PlanReadRes.builder()
                .planName(plan.getName())
                .carrier(plan.getCarrier())
                .mobileDataAmount(plan.getMobileDataAmount())
                .isUltimatedAmount(plan.getIsUltimatedAmount())
                .sellMobileDataCapacityGB(plan.getSellMobileDataCapacityGb())
                .mobileDataType(plan.getMobileDataType())
                .build();
    }
}
