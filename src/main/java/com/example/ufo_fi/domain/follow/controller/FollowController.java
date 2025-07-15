package com.example.ufo_fi.domain.follow.controller;

import com.example.ufo_fi.domain.follow.controller.api.FollowApiSpec;
import com.example.ufo_fi.domain.follow.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingCreateRes;
import com.example.ufo_fi.domain.follow.service.FollowService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class FollowController implements FollowApiSpec {

    private final FollowService followService;

    @Override
    public ResponseEntity<ResponseBody<FollowingCreateRes>> createFollower(Long toId,
        Long fromId) {
        return ResponseEntity.ok(
            ResponseBody
                .success(followService.createFollowing(toId, fromId)));
    }

    @Override
    public ResponseEntity<ResponseBody<FollowerDeleteRes>> deleteFollower(Long followerId,
        Long userId) {
        return ResponseEntity.ok(ResponseBody.success(
            followService.deleteFollower(followerId, userId)
        ));
    }
}
