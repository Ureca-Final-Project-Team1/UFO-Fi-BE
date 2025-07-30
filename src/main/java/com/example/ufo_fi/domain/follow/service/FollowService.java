package com.example.ufo_fi.domain.follow.service;

import com.example.ufo_fi.domain.follow.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowersReadRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingCreateRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingsReadRes;
import com.example.ufo_fi.v2.follow.domain.Follow;
import com.example.ufo_fi.domain.follow.exception.FollowErrorCode;
import com.example.ufo_fi.domain.follow.repository.FollowRepository;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.infrastructure.UserRepository;
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
     * FollowController
     * @param followingId   toId 팔로우할 대상의 ID
     * @param followerId 팔로우를 신청하는 주체(나)의 ID
     * 1. 나를 조회한다.
     * 2. 상대(내가 팔로우할 사람)을 조회한다.
     * 3. 내가 나를 조회한다면, 예외를 발생시킨다.
     */
    @Transactional
    public FollowingCreateRes createFollow(Long followingId, Long followerId) {

        User follower = readUser(followerId); // 신청한 사람(내가 팔로워가 된다.)
        User following = readUser(followingId); // 신청 받은 사람

        if (isFollowerEqualsFollowing(follower, following)) {
            throw new GlobalException(FollowErrorCode.INVALID_REQUEST);
        }

        Follow newFollow = Follow.createFollow(follower, following);
        Follow savedFollow = followRepository.save(newFollow);

        return FollowingCreateRes.from(savedFollow);
    }

    /**
     * FollowController
     * @param followingId : 삭제할 팔로워 (나를 팔로우했던 사람)
     * @param userId   : 나 (팔로우를 당한 사람)
     * 1. 팔로우를 조회한다.
     * 2. 팔로우를 삭제한다.
     * 3. dto 반환
     */
    @Transactional
    public FollowerDeleteRes deleteFollower(Long followingId, Long userId) {
        Follow follow = followRepository.findByFollowingUserIdAndFollowerUserId(followingId, userId)
            .orElseThrow(() -> new GlobalException(FollowErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);

        return FollowerDeleteRes.from(follow);
    }

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

    private boolean isFollowerEqualsFollowing(User follower, User following) {
        return follower.getId().equals(following.getId());
    }

    private User readUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(FollowErrorCode.USER_NOT_FOUND));
    }
}
