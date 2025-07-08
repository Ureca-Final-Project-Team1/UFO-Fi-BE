package com.example.ufo_fi.domain.onboard.dto.response;

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
    private Long id;
    private String name;
    private Carrier carrier;
    private Integer mobileDataAmount;
    private Boolean isUltimatedAmount;
    private Integer sellMobileDataCapacityGB;
    private MobileDataType mobileDataType;

    public static PlanReadRes from(Plan plan) {
        return PlanReadRes.builder()
                .id(plan.getId())
                .name(plan.getName())
                .sellMobileDataCapacityGB(plan.getSellMobileDataCapacityGb())
                .mobileDataType(plan.getMobileDataType())
                .build();
    }
}
