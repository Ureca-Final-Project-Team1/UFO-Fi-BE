package com.example.ufo_fi.v2.follow.domain;


import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.follow.exception.FollowErrorCode;
import com.example.ufo_fi.v2.follow.persistence.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Follow findFollowingIdAndFollowerId(Long followingId, Long userId) {

        return followRepository.findByFollowingUserIdAndFollowerUserId(followingId, userId)
            .orElseThrow(() -> new GlobalException(FollowErrorCode.FOLLOW_NOT_FOUND));
    }

    public Follow saveFollow(Follow follow) {
        try{
            return followRepository.save(follow);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalException(FollowErrorCode.ALREADY_FOLLOW);
        }
    }

    public void deleteFollow(Follow follow) {

        followRepository.delete(follow);
    }

    public Page<Follow> findAllFollowerId(Long userId, PageRequest pageRequest) {

        return followRepository.findAllByFollowerUserId(userId, pageRequest);
    }

    public Page<Follow> findAllFollowingId(Long userId, PageRequest pageRequest) {

        return followRepository.findAllByFollowingUserId(userId, pageRequest);
    }


}
