package com.example.ufo_fi.domain.user.service;

import com.example.ufo_fi.domain.follow.repository.FollowRepository;
import com.example.ufo_fi.domain.notification.entity.NotificationSetting;
import com.example.ufo_fi.domain.notification.repository.NotificationSettingRepository;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.plan.exception.PlanErrorCode;
import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import com.example.ufo_fi.domain.tradepost.entity.TradePost;
import com.example.ufo_fi.domain.tradepost.repository.TradePostRepository;
import com.example.ufo_fi.domain.user.dto.request.*;
import com.example.ufo_fi.domain.user.dto.response.*;
import com.example.ufo_fi.domain.user.entity.*;
import com.example.ufo_fi.domain.user.exception.UserErrorCode;
import com.example.ufo_fi.domain.user.repository.UserAccountRepository;
import com.example.ufo_fi.domain.user.repository.UserPlanRepository;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final boolean ACTIVE_STATUS = true;

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final UserPlanRepository userPlanRepository;
    private final TradePostRepository tradePostRepository;
    private final RandomImageSelector randomImageSelector;
    private final UserAccountRepository userAccountRepository;
    private final NotificationSettingRepository notificationRepository;
    private final RandomNicknameGenerator randomNicknameGenerator;
    private final FollowRepository followRepository;

    /**
     * MyPageUserPlanController 유저의 요금제 정보를 받아오는 메서드
     * 1. UserPlan을 찾아온다.
     * 2. return 한다.
     */
    public UserPlanReadRes readUserPlan(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        UserPlan userPlan = user.getUserPlan();
        if (userPlan == null) {
            throw new GlobalException(UserErrorCode.NO_USER_PLAN);
        }

        Plan plan = userPlan.getPlan();
        if (plan == null) {
            throw new GlobalException(UserErrorCode.NO_PLAN);
        }

        return UserPlanReadRes.of(userPlan, plan);
    }

    /**
     * MyPageUserAccountController 유저의 계좌를 읽어온다.
     * 1. 없을 시 예외를 반환한다.
     * 2. 은행명, 계좌번호
     */
    public AccountReadRes readUserAccount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        UserAccount userAccount = user.getUserAccount();
        if (userAccount == null) {
            throw new GlobalException(UserErrorCode.NO_USER_ACCOUNT);
        }

        return AccountReadRes.from(userAccount);
    }

    /**
     * MyPageUserInfoController 유저의 정보를 받아오는 메서드
     * 1. fetch join을 통해 userPlan까지 다 받아온다.
     * 2. return 한다.
     */
    public UserInfoReadRes readUserAndUserPlan(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        UserPlan userPlan = user.getUserPlan();
        if (userPlan == null) {
            throw new GlobalException(UserErrorCode.NO_USER_PLAN);
        }

        Plan plan = userPlan.getPlan();
        if (plan == null) {
            throw new GlobalException(UserErrorCode.NO_PLAN);
        }

        return UserInfoReadRes.of(user, userPlan, plan);
    }

    public AnotherUserInfoReadRes readAnotherUser(Long anotherUserId, Long userId) {

        User readUser = userRepository.findUserWithUserPlanAndPlan(anotherUserId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        List<TradePost> tradePosts = tradePostRepository.findAllByUser(readUser);

        Long followerCount = followRepository.countByFollowingUser_Id(anotherUserId);

        Long followingCount = followRepository.countByFollowerUser_Id(anotherUserId);

        if(tradePosts.isEmpty()){
            return AnotherUserInfoReadRes.of(readUser, followerCount, followingCount);
        }
        return AnotherUserInfoReadRes.of(readUser, followerCount, followingCount, tradePosts);
    }

    /**
     * MyPageUserPlanController 유저의 요금제 정보를 업데이트하는 메서드
     * 1. fetch join을 통해 userPlan까지 다 받아온다.
     * 2. 유저가 게시물을 올린 상태(sellableMobileDate)와 조금이라도 사용한 상태일 시 예외를 던진다.
     * 3. 요금제 테이블에서 요금제 정보를 받아온다.
     * 4. 영속화된 userPlan을 업데이트하고 return
     */
    @Transactional
    public UserPlanUpdateRes updateUserPlan(Long userId, UserPlanUpdateReq userPlanUpdateReq) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        UserPlan userPlan = user.getUserPlan();
        if(userPlan == null){
            throw new GlobalException(UserErrorCode.NO_USER_PLAN);
        }

        if (tradePostRepository.existsByUser(user)) {
            throw new GlobalException(UserErrorCode.CANT_UPDATE_USER_PLAN);
        }

        Plan plan = planRepository.findById(userPlanUpdateReq.getPlanId())
                .orElseThrow(() -> new GlobalException(PlanErrorCode.INVALID_PLAN));

        if (!Objects.equals(userPlan.getSellableDataAmount(), plan.getSellMobileDataCapacityGb())) {
            throw new GlobalException(UserErrorCode.CANT_UPDATE_USER_PLAN);
        }

        userPlan.update(plan);

        return UserPlanUpdateRes.from(userPlan);
    }

    /**
     * MyPageUserAccountController 유저의 계좌를 등록한다.
     * 1. 이미 등록되었을 시 예외를 반환한다.
     * 2. 은행명, 계좌번호, 비밀 번호를 받는다.
     */
    @Transactional
    public AccountCreateRes createUserAccount(Long userId, AccountCreateReq accountCreateReq) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        if (user.getUserAccount() != null) {
            throw new GlobalException(UserErrorCode.ALREADY_ACCOUNT_EXIST);
        }

        UserAccount userAccount = UserAccount.from(accountCreateReq);
        user.registerUserAccount(userAccount);
        userAccountRepository.save(userAccount);

        return AccountCreateRes.from(userAccount);
    }

    /**
     * MyPageUserInfoController 회원가입 페이지에서 유저와 유저 정보를 업데이트한다.
     * 1. 유저를 찾아와 기본 정보(랜덤 닉네임, 랜덤 이미지, 실명, 핸드폰 번호)를 업데이트
     * 2. 유저 요금제를 등록/연관관계 등록
     */
    @Transactional
    public SignupRes updateUserAndUserPlan(Long userId, SignupReq signupReq) {
        User user = signupUser(userId, signupReq.getUserInfoReq());
        registerUserPlan(user, signupReq.getUserPlanReq());
        return SignupRes.from(user);
    }

    //유저를 찾아와 기본 정보(랜덤 닉네임, 랜덤 이미지, 실명, 핸드폰 번호)를 업데이트
    public UserRoleReadRes getUserRole(Role role) {
        return UserRoleReadRes.from(role);
    }

    @Transactional
    public UserNicknameUpdateRes updateUserNicknames(Long userId, UserNicknameUpdateReq userNicknameUpdateReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        user.updateNickname(userNicknameUpdateReq);

        return UserNicknameUpdateRes.from(user);
    }

    private User signupUser(Long userId, UserInfoReq userInfoReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));

        if (user.getRole() == Role.ROLE_USER || user.getRole() == Role.ROLE_ADMIN) {
            throw new GlobalException(UserErrorCode.ALREADY_USER_SIGNUP);
        }

        String randomNickname = randomNicknameGenerator.generate();
        ProfilePhoto randomProfilePhoto = randomImageSelector.select();

        user.signup(userInfoReq, randomNickname, randomProfilePhoto, ACTIVE_STATUS, Role.ROLE_USER);
        return user;
    }

    //유저 요금제를 등록/연관관계 등록
    private void registerUserPlan(User user, UserPlanReq userPlanReq) {
        Plan plan = planRepository.findById(userPlanReq.getPlanId())
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_PLAN));

        UserPlan userPlan = UserPlan.from(plan);
        userPlanRepository.save(userPlan);

        user.registerUserPlan(userPlan);
    }
}
