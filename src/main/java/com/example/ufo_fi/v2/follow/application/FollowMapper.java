package com.example.ufo_fi.v2.follow.application;

import com.example.ufo_fi.v2.follow.domain.Follow;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowerReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowersReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingCreateRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingsReadRes;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {

    public FollowingCreateRes toFollowingCreateRes(final Follow follow) {
        return FollowingCreateRes.builder()
            .id(follow.getId())
            .build();
    }

    public FollowerDeleteRes toFollowerDeleteRes(final Follow follow) {
        return FollowerDeleteRes.builder()
            .id(follow.getId())
            .build();
    }

    public FollowingsReadRes toFollowingsReadRes(final Page<Follow> follows) {
        return FollowingsReadRes.builder()
            .followingsReadRes(follows
                .map(follow -> FollowingReadRes.from(follow.getFollowingUser())).toList())
            .build();
    }

    public FollowersReadRes toFollowerReadRes(final Page<Follow> follows) {
        return FollowersReadRes.builder()
            .followersReadRes(follows
                .map(follow -> FollowerReadRes.from(follow.getFollowerUser())).toList())
            .build();
    }

}
