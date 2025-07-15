package com.example.ufo_fi.domain.user.dto.request;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.MobileDataType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPlanReq {
    private Long planId;
    private String planName;
}
