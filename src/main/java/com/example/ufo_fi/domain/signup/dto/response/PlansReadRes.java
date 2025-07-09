package com.example.ufo_fi.domain.signup.dto.response;

import com.example.ufo_fi.domain.plan.entity.Plan;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlansReadRes {
    private List<PlanReadRes> plans;

    public static PlansReadRes from(List<Plan> plans) {
        return PlansReadRes.builder()
                .plans(plans.stream().map(PlanReadRes::from).toList())
                .build();
    }
}
