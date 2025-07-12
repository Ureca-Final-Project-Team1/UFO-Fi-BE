package com.example.ufo_fi.domain.user.service;

import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.dto.response.UserInfoReadRes;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.exception.UserErrorCode;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TradePostRepository tradePostRepository;

    public UserInfoReadRes readUser(Long userId) {
        User user = userRepository.findUserWithUserPlan(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));
        com.example.ufo_fi.domain.userplan.entity.UserPlan userPlan = user.getUserPlan();

        return UserInfoReadRes.of(user, userPlan);
    }
}
