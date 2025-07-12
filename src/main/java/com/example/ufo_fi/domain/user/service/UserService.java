package com.example.ufo_fi.domain.user.service;

import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.domain.user.dto.response.UserInfoReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserPlanReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.exception.UserErrorCode;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TradePostRepository tradePostRepository;

    public UserPlanReadRes readUserPlan(Long userId) {
        User user = userRepository.findUserWithUserPlan(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER_AND_USER_PLAN));
        UserPlan userPlan = user.getUserPlan();

        return UserPlanReadRes.from(userPlan);
    }

    public UserInfoReadRes readUser(Long userId) {
        User user = userRepository.findUserWithUserPlan(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));
        UserPlan userPlan = user.getUserPlan();

        return UserInfoReadRes.of(user, userPlan);
    }

    @Transactional
    public UserPlanUpdateRes updateUserPlan(Long userId, UserPlanUpdateReq userPlanUpdateReq) {
        User user = userRepository.findUserWithUserPlan(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER_AND_USER_PLAN));
        UserPlan userPlan = user.getUserPlan();

        if(tradePostRepository.existsByUser(user)){
            throw new GlobalException(UserErrorCode.CANT_UPDATE_USER_PLAN);
        }

        if(userPlan.getSellableDataAmount() != userPlan.getSellMobileDataCapacityGb()){
            throw new GlobalException(UserErrorCode.CANT_UPDATE_USER_PLAN);
        }

        Plan plan = planRepository.findById(userPlanUpdateReq.getPlanId())
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_UPDATE_PLAN));

        userPlan.update(plan);

        return UserPlanUpdateRes.from(userPlan);
    }
}
