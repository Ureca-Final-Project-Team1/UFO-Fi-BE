package com.example.ufo_fi.domain.signup.dto.request;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPlanReq {
    private String planName;
    private Carrier carrier;
    private MobileDataType mobileDataAmount;
    private Boolean isUltimatedAmount;
    private Integer sellMobileDataCapacityGB;
    private MobileDataType mobileDataType;
}
