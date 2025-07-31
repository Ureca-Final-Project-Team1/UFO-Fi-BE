package com.example.ufo_fi.domain.follow.dto.response;

import com.example.ufo_fi.v2.follow.domain.Follow;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowersReadRes {
    private List<FollowerReadRes> followersReadRes;

    public static FollowersReadRes from(final List<Follow> follows) {
        return FollowersReadRes.builder()
                .followersReadRes(follows.stream()
                        .map(follow -> FollowerReadRes.from(follow.getFollowerUser())).toList())
                .build();
    }
}
