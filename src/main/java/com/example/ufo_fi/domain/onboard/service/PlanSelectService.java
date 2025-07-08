package com.example.ufo_fi.domain.onboard.service;

import com.example.ufo_fi.domain.onboard.dto.request.PlansReadReq;
import com.example.ufo_fi.domain.onboard.dto.request.UserPlanCreateReq;
import com.example.ufo_fi.domain.onboard.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.onboard.exception.PlanSelectErrorCode;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import com.example.ufo_fi.domain.userplan.repository.UserPlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanSelectService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final UserPlanRepository userPlanRepository;

    /**
     * 통신사를 통해 해당 통신사의 모든 요금제를 가져온다.
     * 1. 통신사로 요금제들 찾기
     * 2. DTO 변환 후 return
     */
    public PlansReadRes readPlans(PlansReadReq plansReadReq) {
        List<Plan> plans = planRepository.findAllByCarrier(plansReadReq.getCarrier());
        return PlansReadRes.from(plans);
    }

    /**
     * 요금제를 받아, 회원 요금제 테이블에 새롭게 추가한다.
     * 1. 유저 프록시 생성
     * 2. 유저 요금제 생성
     * 3. 유저 요금제 저장
     */
    @Transactional
    public Void createUserPlan(Long userId, UserPlanCreateReq userPlanCreateReq) {
        User user = userRepository.getReferenceById(userId);
        UserPlan userPlan = UserPlan.of(userPlanCreateReq, user);
        userPlanRepository.save(userPlan);
        return null;
    }
}
