package com.example.ufo_fi.v2.user.application;

import com.example.ufo_fi.v2.user.presentation.dto.request.UserNicknameUpdateReq;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserInfoReadRes;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.plan.domain.PlanManager;
import com.example.ufo_fi.v2.user.presentation.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.v2.follow.domain.FollowManager;
import com.example.ufo_fi.v2.tradepost.application.TradePostManager;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserNicknameUpdateRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import com.example.ufo_fi.v2.userplan.domain.UserPlanManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserManager userManager;
    private final UserPlanManager userPlanManager;
    private final PlanManager planManager;
    private final TradePostManager tradePostManager;
    private final FollowManager followManager;
    private final UserMapper userMapper;

    public UserRoleReadRes readUserInfo(Long userId, Role role) {
        User user = userManager.findById(userId);
        String userPhoneNumber = userManager.getPhoneNumber(user);
        return userMapper.toUserRoleReadRes(role, userPhoneNumber);
    }

    public AnotherUserInfoReadRes readAnotherUser(Long anotherUserId) {
        User anotherUser = userManager.findById(anotherUserId);
        List<TradePost> tradePosts = tradePostManager.findPostsByAnotherUser(anotherUser);

        Long followerCount = followManager.countByFollowerUserId(anotherUserId);
        Long followingCount = followManager.countByFollowingUserId(anotherUserId);

        return userMapper.toAnotherUserInfoReadRes(
            anotherUser, tradePosts, followerCount, followingCount
        );
    }

    public UserInfoReadRes readUserAndUserPlan(Long userId) {
        User user = userManager.findById(userId);
        UserPlan userPlan = userPlanManager.findByUser(user);
        Plan plan = planManager.findPlanById(userPlan.getPlan().getId());

        return userMapper.toUserInfoRes(user, userPlan, plan);
    }

    public UserNicknameUpdateRes updateUserNicknames(
        Long userId, UserNicknameUpdateReq userNicknameUpdateReq
    ) {
        User user = userManager.findById(userId);
        userManager.updateUserNickname(user, userNicknameUpdateReq.getNickname(), userId);

        return userMapper.toUserNicknameUpdateRes(user);
    }
}
