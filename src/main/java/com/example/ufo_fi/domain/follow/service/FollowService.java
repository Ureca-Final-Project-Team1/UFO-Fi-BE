package com.example.ufo_fi.domain.follow.service;

import com.example.ufo_fi.domain.follow.dto.response.FollowersReadRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingsReadRes;
import com.example.ufo_fi.domain.follow.entity.Follow;
import com.example.ufo_fi.domain.follow.repository.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    /**
     * MyPageFollowController
     * 내가 팔로워 일 시 상대방은 팔로잉(당하는 사람)이다.
     */
    public FollowingsReadRes readFollowings(Long userId) {
        List<Follow> follows = followRepository.findAllByFollowerUserId(userId);
        return FollowingsReadRes.from(follows);
    }

    /**
     * MyPageFollowController
     * 내가 팔로잉(당하는 사람) 일 시 상대방은 팔로워(가해자)이다.
     */
    public FollowersReadRes readFollowers(Long userId) {
        List<Follow> follows = followRepository.findAllByFollowingUserId(userId);
        return FollowersReadRes.from(follows);
    }
}
