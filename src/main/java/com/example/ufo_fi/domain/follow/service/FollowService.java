package com.example.ufo_fi.domain.follow.service;

import com.example.ufo_fi.domain.follow.dto.response.FollowersReadRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingsReadRes;
import com.example.ufo_fi.domain.follow.entity.Follow;
import com.example.ufo_fi.domain.follow.repository.FollowRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    /**
     * 내가 팔로워 일 시 상대방은 팔로잉(당하는 사람)이다.
     */
    public FollowingsReadRes readFollowings(Long userId) {
        List<Follow> follows = followRepository.findAllByFollowerUser(userId);
        return FollowingsReadRes.from(follows);
    }

    /**
     * 내가 팔로잉(당하는 사람) 일 시 상대방은 팔로워(가해자)이다.
     */
    public FollowersReadRes readFollowers(Long userId) {
        List<Follow> follows = followRepository.findAllByFollowingUser(userId);
        return FollowersReadRes.from(follows);
    }
}
