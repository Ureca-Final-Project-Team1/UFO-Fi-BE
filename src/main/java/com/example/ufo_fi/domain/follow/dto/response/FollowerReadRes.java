package com.example.ufo_fi.domain.follow.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerReadRes {
    private Long id;
    private String nickname;
    private String profilePhotoUrl;

    public static FollowerReadRes from(final User followingUser) {
        return FollowerReadRes.builder()
                .id(followingUser.getId())
                .nickname(followingUser.getNickname())
                .profilePhotoUrl(followingUser.getProfilePhoto().getProfilePhotoUrl())
                .build();
    }
}
