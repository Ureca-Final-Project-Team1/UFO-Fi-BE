package com.example.ufo_fi.domain.plan.service;

import com.example.ufo_fi.domain.plan.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.plan.exception.PlanErrorCode;
import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    /**
     * 통신사를 받아, 요금제들을 넘겨주는 메서드
     * 1. rawCarrier를 받는다.
     * 2. rawCarrier가 null이거나, rawCarrier가 없을 시 예외를 던진다.
     * 3. LG U+를 Enum 값인 LGU로 파싱
     * 4. 요금제들을 찾아, return
     */
    public PlansReadRes readPlans(String rawCarrier) {
        if(rawCarrier == null || rawCarrier.trim().isEmpty()){
            throw new GlobalException(PlanErrorCode.INVALID_CARRIER);
        }

        if(rawCarrier.startsWith("LG")){
            rawCarrier = "LGU";
        }

        Carrier carrier = Carrier.valueOf(rawCarrier);
        List<Plan> plans = planRepository.findAllByCarrier(carrier);

        return PlansReadRes.from(plans);
    }
}
