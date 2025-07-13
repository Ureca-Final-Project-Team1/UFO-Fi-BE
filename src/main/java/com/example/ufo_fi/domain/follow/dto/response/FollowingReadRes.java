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
public class FollowingReadRes {
    private Long id;
    private String nickname;
    private String profilePhotoUrl;

    public static FollowingReadRes from(User followerUser) {
        return FollowingReadRes.builder()
                .id(followerUser.getId())
                .nickname(followerUser.getNickname())
                .profilePhotoUrl(followerUser.getProfilePhoto().getProfilePhotoUrl())
                .build();
    }
}
