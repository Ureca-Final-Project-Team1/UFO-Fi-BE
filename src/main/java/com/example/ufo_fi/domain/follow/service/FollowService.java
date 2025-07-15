package com.example.ufo_fi.domain.follow.service;

import com.example.ufo_fi.domain.follow.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowersReadRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingCreateRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingsReadRes;
import com.example.ufo_fi.domain.follow.entity.Follow;
import com.example.ufo_fi.domain.follow.exception.FollowErrorCode;
import com.example.ufo_fi.domain.follow.repository.FollowRepository;
import com.example.ufo_fi.domain.user.entity.User;
import com.example.ufo_fi.domain.user.repository.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    /**
     * @param toId   toId 팔로우할 대상의 ID
     * @param fromId fromId 팔로우를 신청하는 주체(나)의 ID
     * @return
     */
    @Transactional
    public FollowingCreateRes createFollowing(Long toId, Long fromId) {

        User follower = getUser(fromId); // 신청한 사람
        User following = getUser(toId); // 신청 받은 사람

        if (follower.getId().equals(following.getId())) {

            throw new GlobalException(FollowErrorCode.INVALID_REQUEST);
        }

        if (followRepository.existsByFollowerUserAndFollowingUser(follower, following)) {

            throw new GlobalException(FollowErrorCode.FOLLOW_ALREADY_EXISTS);
        }

        Follow newFollow = Follow.createFollow(follower, following);

        Follow savedFollow = followRepository.save(newFollow);

        return new FollowingCreateRes(savedFollow.getId());
    }

    /**
     * @param fromId : 삭제할 팔로워 (나를 팔로우했던 사람)
     * @param toId   : 나 (팔로우를 당한 사람)
     * @return
     */
    @Transactional
    public FollowerDeleteRes deleteFollower(Long fromId, Long toId) {

        User me = getUser(toId); //로그인 한 사용자

        User follower2Remove = getUser(fromId);

        Follow follow = followRepository.findByFollowingUserAndFollowerUser(me, follower2Remove)
            .orElseThrow(() -> new GlobalException(FollowErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);

        return new FollowerDeleteRes(follower2Remove.getId());
    }

    /**
     * MyPageFollowController 내가 팔로워 일 시 상대방은 팔로잉(당하는 사람)이다.
     */
    public FollowingsReadRes readFollowings(Long userId) {
        List<Follow> follows = followRepository.findAllByFollowerUserId(userId);
        return FollowingsReadRes.from(follows);
    }

    /**
     * MyPageFollowController 내가 팔로잉(당하는 사람) 일 시 상대방은 팔로워(가해자)이다.
     */
    public FollowersReadRes readFollowers(Long userId) {
        List<Follow> follows = followRepository.findAllByFollowingUserId(userId);
        return FollowersReadRes.from(follows);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(FollowErrorCode.USER_NOT_FOUND));
    }
}
