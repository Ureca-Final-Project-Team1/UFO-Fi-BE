package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.plan.entity.Plan;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.entity.UserPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReadRes {
    private String nickname;
    private String email;
    private Integer sellMobileDataCapacityGb;
    private Integer sellableDataAmount;
    private Integer zetAsset;

    public static UserInfoReadRes of(final User user, final UserPlan userPlan, final Plan plan){
        return UserInfoReadRes.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .sellMobileDataCapacityGb(plan.getSellMobileDataCapacityGb())
                .sellableDataAmount(userPlan.getSellableDataAmount())
                .zetAsset(user.getZetAsset())
                .build();
    }
}
