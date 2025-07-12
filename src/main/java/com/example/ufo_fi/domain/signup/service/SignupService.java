package com.example.ufo_fi.domain.signup.service;

import com.example.ufo_fi.domain.notification.entity.NotificationSetting;
import com.example.ufo_fi.domain.notification.repository.NotificationRepository;
import com.example.ufo_fi.domain.plan.entity.Carrier;
import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.plan.repository.PlanRepository;
import com.example.ufo_fi.domain.profilephoto.entity.ProfilePhoto;
import com.example.ufo_fi.domain.signup.dto.request.SignupReq;
import com.example.ufo_fi.domain.signup.dto.response.PlansReadRes;
import com.example.ufo_fi.domain.signup.dto.response.SignupRes;
import com.example.ufo_fi.domain.signup.exception.SignupErrorCode;
import com.example.ufo_fi.domain.user.UserRepository;
import com.example.ufo_fi.domain.user.entity.Role;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import com.example.ufo_fi.domain.userplan.repository.UserPlanRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignupService {
    private static final boolean ACTIVE_STATUS = true;

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final UserPlanRepository userPlanRepository;
    private final RandomImageSelector randomImageSelector;
    private final NotificationRepository notificationRepository;
    private final RandomNicknameGenerator randomNicknameGenerator;

    /**
     * @RequestParam으로 통신사를 받아와 목록을 반환한다.
     * rawCarrier : LG U+ 는 enum의 특성 상 +와 같은 것이 붙을 수 없어, 직접 LGU로 변환 후 Enum으로 변환한다.
     * 변환된 carrier로 DB에서 조회해온다.
     */
    public PlansReadRes readPlans(String rawCarrier) {
        if (rawCarrier.startsWith("LG")) {
            rawCarrier = "LGU";
        }

        Carrier carrier = Carrier.valueOf(rawCarrier);
        List<Plan> plans = planRepository.findAllByCarrier(carrier);

        return PlansReadRes.from(plans);
    }

    /**
     * 회원가입 페이지에서 유저와 유저 정보를 업데이트한다.
     * 1. 유저를 찾아와 기본 정보(랜덤 닉네임, 랜덤 이미지, 실명, 핸드폰 번호)를 업데이트
     * 2. 유저 요금제를 등록/연관관계 등록
     */
    @Transactional
    public SignupRes updateUserAndUserPlan(Long userId, SignupReq signupReq) {
        User user = signupUser(userId, signupReq);
        registerUserPlan(signupReq, user);
        setNotifications(user);

        return SignupRes.from(user);
    }

    //유저를 찾아와 기본 정보(랜덤 닉네임, 랜덤 이미지, 실명, 핸드폰 번호)를 업데이트
    private User signupUser(Long userId, SignupReq signupReq) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(SignupErrorCode.NO_USER));

        String randomNickname = randomNicknameGenerator.generate();
        ProfilePhoto randomProfilePhoto = randomImageSelector.select();

        user.signup(signupReq.getUserInfoReq(), randomNickname, randomProfilePhoto, ACTIVE_STATUS, Role.ROLE_USER);
        return user;
    }

    //유저 요금제를 등록/연관관계 등록
    private void registerUserPlan(SignupReq signupReq, User user) {
        UserPlan userPlan = UserPlan.from(signupReq.getUserPlanReq());
        user.registerUserPlan(userPlan);
        userPlanRepository.save(userPlan);
    }

    //알림 설정을 초기화
    private void setNotifications(User user) {
        NotificationSetting notification = NotificationSetting.from(user);
        notificationRepository.save(notification);
    }
}
