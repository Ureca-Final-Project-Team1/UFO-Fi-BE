package com.example.ufo_fi.domain.user.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AnotherUserInfoReadRes {

    private final Long userId;
    private final String nickname;
    private final String profileImageUrl;
    private final Long followerCount;
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