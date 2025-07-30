package com.example.ufo_fi.v2.plan.domain;

import com.example.ufo_fi.v2.plan.exception.PlanErrorCode;
import com.example.ufo_fi.v2.plan.infrastructure.PlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanManager {

    private final PlanRepository planRepository;

    public List<Plan> findPlansByRawCarrier(String rawCarrier) {
        if(rawCarrier == null || rawCarrier.trim().isEmpty()){
            throw new GlobalException(PlanErrorCode.INVALID_CARRIER);
        }

        if(rawCarrier.startsWith("LG")){
            rawCarrier = "LGU";
        }

        return planRepository.findAllByCarrier(Carrier.valueOf(rawCarrier));
    }
}
