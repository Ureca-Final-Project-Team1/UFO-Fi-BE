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

    /**
     * 유저의 요금제 정보를 받아오는 메서드
     * 1. fetch join을 통해 userPlan까지 다 받아온다.
     * 2. return 한다.
     */
    public UserPlanReadRes readUserPlan(Long userId) {
        User user = userRepository.findUserWithUserPlan(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER_AND_USER_PLAN));
        UserPlan userPlan = user.getUserPlan();

        return UserPlanReadRes.from(userPlan);
    }

    /**
     * 유저의 정보를 받아오는 메서드
     * 1. fetch join을 통해 userPlan까지 다 받아온다.
     * 2. return 한다.
     */
    public UserInfoReadRes readUser(Long userId) {
        User user = userRepository.findUserWithUserPlan(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));
        UserPlan userPlan = user.getUserPlan();

        return UserInfoReadRes.of(user, userPlan);
    }

    /**
     * 유저의 요금제 정보를 업데이트하는 메서드
     * 1. fetch join을 통해 userPlan까지 다 받아온다.
     * 2. 유저가 게시물을 올린 상태(sellableMobileDate)와 조금이라도 사용한 상태일 시 예외를 던진다.
     * 3. 요금제 테이블에서 요금제 정보를 받아온다.
     * 4. 영속화된 userPlan을 업데이트하고 return
     */
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
