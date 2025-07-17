package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AnotherUserInfoReadRes {

    @Schema(description = "다른 사용자 식별 번호")
    private final Long userId;

    @Schema(description = "다른 사용자 닉네임")
    private final String nickname;

    @Schema(description = "다른 사용자 프로필 사진")
    private final String profileImageUrl;

    @Schema(description = "다른 사용자 팔로워 수")
    private final Long followerCount;

    @Schema(description = "다른 사용자 팔로잉 수")
    private final Long followingCount;

    public static AnotherUserInfoReadRes of(User user, Long followerCount, Long followingCount) {

        String imageUrl = null;

        if (user.getProfilePhoto() != null) {
            imageUrl = String.valueOf(user.getProfilePhoto());
        }
        return new AnotherUserInfoReadRes(
            user.getId(),
            user.getNickname(),
            imageUrl,
            followerCount,
            followingCount
        );
    }
}