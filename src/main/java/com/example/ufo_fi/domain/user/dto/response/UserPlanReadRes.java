package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPlanReadRes {
    private Long planId;
    private String planName;
    private Carrier carrier;
    private MobileDataType mobileDataType;
    private Integer sellMobileDataCapacity;

    public static UserPlanReadRes of(UserPlan userPlan, Plan plan) {
        return UserPlanReadRes.builder()
                .planId(plan.getId())
                .planName(plan.getName())
                .carrier(plan.getCarrier())
                .mobileDataType(plan.getMobileDataType())
                .sellMobileDataCapacity(plan.getSellMobileDataCapacityGb())
                .build();
    }
}
