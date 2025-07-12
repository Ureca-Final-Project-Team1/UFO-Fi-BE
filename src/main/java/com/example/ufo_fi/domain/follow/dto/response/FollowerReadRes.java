package com.example.ufo_fi.domain.follow.dto.response;

import com.example.ufo_fi.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerReadRes {
    private Long id;
    private String nickname;
    private String profilePhotoUrl;

    public static FollowerReadRes from(final User user) {
        return FollowerReadRes.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profilePhotoUrl(user.getProfilePhoto().getProfilePhotoUrl())
                .build();
    }
}
