package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
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

    public static UserPlanReadRes from(UserPlan userPlan) {
        return UserPlanReadRes.builder()
                .planId(userPlan.getId())
                .planName(userPlan.getPlanName())
                .carrier(userPlan.getCarrier())
                .mobileDataType(userPlan.getMobileDataType())
                .sellMobileDataCapacity(userPlan.getSellMobileDataCapacityGb())
                .build();
    }
}
