package com.example.ufo_fi.v2.follow.domain;

import com.example.ufo_fi.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowManager {

    private final FollowRepository followRepository;

    public Long countByFollowingUserId(Long anotherUserId) {
        return followRepository.countByFollowingUser_Id(anotherUserId);
    }

    public Long countByFollowerUserId(Long anotherUserId) {
        return followRepository.countByFollowerUser_Id(anotherUserId);
    }
}
