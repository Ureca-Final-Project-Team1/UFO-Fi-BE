package com.example.ufo_fi.v2.follow.domain;


import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.follow.exception.FollowErrorCode;
import com.example.ufo_fi.v2.follow.infrastructure.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowManager {

    private final FollowRepository followRepository;

    public Follow findFollowingIdAndFollowerId(Long followingId, Long userId) {

        return followRepository.findByFollowingUserIdAndFollowerUserId(followingId, userId)
            .orElseThrow(() -> new GlobalException(
                FollowErrorCode.FOLLOW_NOT_FOUND));
    }

    public Follow saveFollow(Follow follow) {

        return followRepository.save(follow);
    }

    public void deleteFollow(Follow follow) {

        followRepository.delete(follow);
    }

    public List<Follow> findAllFollowerId(Long userId) {

        return followRepository.findAllByFollowerUserId(userId);
    }

    public List<Follow> findAllFollowingId(Long userId) {

        return followRepository.findAllByFollowingUserId(userId);
    }


}
