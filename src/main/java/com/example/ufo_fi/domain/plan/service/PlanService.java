package com.example.ufo_fi.domain.plan.service;

import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public PlansReadRes readPlans(String rawCarrier) {
        if(rawCarrier.startsWith("LG")){
            rawCarrier = "LGU";
        }

        Carrier carrier = Carrier.valueOf(rawCarrier);
        List<Plan> plans = planRepository.findAllByCarrier(carrier);

        return PlansReadRes.from(plans);
    }
}
