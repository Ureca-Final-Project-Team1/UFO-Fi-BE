package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
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

    public static SignupRes from(User user){
        return SignupRes.builder()
                .nickname(user.getNickname())
                .profilePhotoUrl(user.getProfilePhoto().getProfilePhotoUrl())
                .build();
    }
}
