package com.example.ufo_fi.domain.onboard.dto.request;

import com.example.ufo_fi.domain.plan.entity.Carrier;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlansReadReq {
    private Carrier carrier;
}
