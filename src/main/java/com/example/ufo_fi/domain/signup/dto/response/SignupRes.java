package com.example.ufo_fi.domain.signup.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.userplan.entity.UserPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRes {
    private String nickname;
    private String profilePhotoUrl;

    public static SignupRes of(User user, UserPlan userPlan){
        return SignupRes.builder()
                .nickname(user.getNickname())
                .profilePhotoUrl(user.getProfilePhoto().getProfilePhotoUrl())
                .build();
    }
}
