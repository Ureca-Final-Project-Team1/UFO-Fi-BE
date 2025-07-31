package com.example.ufo_fi.v2.follow.application;


import com.example.ufo_fi.v2.follow.domain.Follow;
import com.example.ufo_fi.v2.follow.domain.FollowManager;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowersReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingCreateRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingsReadRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowManager followManager;
    private final UserManager userManager;
    private final FollowMapper followMapper;

    /**
     * FollowController
     *
     * @param followingId toId 팔로우할 대상의 ID
     * @param followerId  팔로우를 신청하는 주체(나)의 ID
     *                    1. 나를 조회한다.
     *                    2. 상대(내가 팔로우할 사람)을 조회한다.
     *                    3. 내가 나를 조회한다면, 예외를 발생시킨다.
     */
    @Transactional
    public FollowingCreateRes createFollow(Long followingId, Long followerId) {

        User follower = userManager.findById(followerId); // 신청한 사람(내가 팔로워가 된다.)
        User following = userManager.findById(followingId); // 신청 받은 사람

        Follow newFollow = Follow.createFollow(follower, following);

        Follow savedFollow = followManager.saveFollow(newFollow);

        return followMapper.toFollowingCreateRes(savedFollow);
    }

    /**
     * FollowController
     *
     * @param followingId : 삭제할 팔로워 (나를 팔로우했던 사람)
     * @param userId      : 나 (팔로우를 당한 사람)
     *                    1. 팔로우를 조회한다.
     *                    2. 팔로우를 삭제한다.
     *                    3. dto 반환
     */
    @Transactional
    public FollowerDeleteRes deleteFollower(Long followingId, Long userId) {

        Follow follow = followManager.findFollowingIdAndFollowerId(followingId, userId);

        followManager.deleteFollow(follow);

        return followMapper.toFollowerDeleteRes(follow);
    }

    /**
     * MyPageFollowController 내가 팔로워 일 시 상대방은 팔로잉(당하는 사람)이다.
     */
    public FollowingsReadRes readFollowings(Long userId) {

        List<Follow> follows = followManager.findAllFollowerId(userId);

        return followMapper.toFollowingsReadRes(follows);
    }

    /**
     * MyPageFollowController 내가 팔로잉(당하는 사람) 일 시 상대방은 팔로워(가해자)이다.
     */
    public FollowersReadRes readFollowers(Long userId) {

        List<Follow> follows = followManager.findAllFollowingId(userId);

        return followMapper.toFollowerReadRes(follows);
    }
    

}
