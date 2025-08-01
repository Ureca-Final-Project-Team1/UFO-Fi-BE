package com.example.ufo_fi.v2.follow.application;


import com.example.ufo_fi.v2.follow.domain.Follow;
import com.example.ufo_fi.v2.follow.domain.FollowManager;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowersReadRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingCreateRes;
import com.example.ufo_fi.v2.follow.presentation.dto.response.FollowingsReadRes;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowManager followManager;
    private final UserManager userManager;
    private final FollowMapper followMapper;

    /**
     * @param followingId : 내가 팔로우할 유저
     * @param followerId : 나
     *
     *                   1. 내가 팔로우할 유저를 조회한다.
     *                   2. 나를 찾는다.
     *                   3. dto 반환
     */
    @Transactional
    public FollowingCreateRes createFollow(Long followingId, Long followerId) {
        User follower = userManager.findById(followerId);
        User following = userManager.findById(followingId);

        Follow newFollow = Follow.of(follower, following);
        Follow savedFollow = followManager.saveFollow(newFollow);

        return followMapper.toFollowingCreateRes(savedFollow);
    }

    /**
     * @param followingId : 삭제할 팔로워 (나를 팔로우했던 사람)
     * @param userId      : 나 (팔로우를 당한 사람)
     *
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
     * @param userId : 나
     * @param page : 페이지네이션
     *
     *             1. 페이지네이션을 적용한 팔로우 조회
     *             2. dto 반환
     */
    public FollowingsReadRes readFollowings(Long userId, int page) {
        Page<Follow> follows = followManager.findAllFollowerId(userId, PageRequest.of(page, 10));

        return followMapper.toFollowingsReadRes(follows);
    }

    /**
     * @param userId : 나
     * @param page : 페이지네이션
     *
     *             1. 페이지네이션을 적용한 팔로우 조회
     *             2. dto 반환
     */
    public FollowersReadRes readFollowers(Long userId, int page) {
        Page<Follow> follows = followManager.findAllFollowingId(userId, PageRequest.of(page, 10));

        return followMapper.toFollowerReadRes(follows);
    }
    

}
