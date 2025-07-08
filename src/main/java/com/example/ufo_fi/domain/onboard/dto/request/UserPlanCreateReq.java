package com.example.ufo_fi.domain.onboard.dto.request;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPlanCreateReq {
    private Carrier carrier;
    private String planName;
    private MobileDataType mobileDataType;
    private Integer sellMobileDataCapacityGb;
}
