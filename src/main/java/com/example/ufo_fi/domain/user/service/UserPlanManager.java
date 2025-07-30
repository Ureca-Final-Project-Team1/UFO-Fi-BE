package com.example.ufo_fi.domain.user.service;


import com.example.ufo_fi.domain.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import com.example.ufo_fi.domain.user.repository.UserPlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlanManager {

    private final UserPlanRepository userPlanRepository;

    public UserPlan validateUserPlanExistence(User user) {
        return userPlanRepository.findByUser(user)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_PLAN_NOT_FOUND));
    }

}
