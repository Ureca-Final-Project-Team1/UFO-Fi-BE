package com.example.ufo_fi.domain.user.service;

import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.dto.request.UserPlanUpdateReq;
import com.example.ufo_fi.domain.user.dto.request.ZetPurchaseReq;
import com.example.ufo_fi.domain.user.dto.response.UserInfoReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserPlanReadRes;
import com.example.ufo_fi.domain.user.dto.response.UserPlanUpdateRes;
import com.example.ufo_fi.domain.user.dto.response.ZetPurchaseRes;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.exception.UserErrorCode;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import com.example.ufo_fi.global.exception.GlobalException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TradePostRepository tradePostRepository;
    private final SlackExceptionNotification slackExceptionNotification;

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

    /**
     * Zet을 구매하는 로직
     * 1. 사용자를 확인한다.
     * 2. 가상 PG 결제 승인을 받는다.(2차 MVP 서버 분리 고려)
     * 3. 결제 이력을 저장한다.
     * 4. 유저 Zet을 적립한다.
     * 5. 응답을 반환한다.
     */
    @Transactional
    public ZetPurchaseRes updateZet(Long userId, ZetPurchaseReq zetPurchaseReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        boolean paymentApproved = true;

        if(!paymentApproved){
            slackExceptionNotification.request(zetPurchaseReq);
            throw new GlobalException(UserErrorCode.PG_PAYMENT_ERROR);
        }

        //user.increaseZet(zetPurchaseReq.getPurchaseZet());
        return null;
    }
}
