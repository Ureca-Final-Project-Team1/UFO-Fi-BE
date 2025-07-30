package com.example.ufo_fi.v2.userplan.domain;


import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserPlanReq;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.userplan.infrastructure.UserPlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlanManager {

    private final EntityManager entityManager;
    private final UserPlanRepository userPlanRepository;

    public UserPlan validateUserPlanExistence(User user) {
        return userPlanRepository.findByUser(user)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND));
    }

    public void saveUserPlan(User user, Plan plan, UserPlanReq userPlanReq) {
        UserPlan userPlan = UserPlan.of(plan, user);
        userPlanRepository.save(userPlan);
    }
}
