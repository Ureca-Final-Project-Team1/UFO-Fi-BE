package com.example.ufo_fi.domain.follow.controller;

import com.example.ufo_fi.domain.follow.controller.api.FollowApiSpec;
import com.example.ufo_fi.domain.follow.dto.response.FollowerDeleteRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingCreateRes;
import com.example.ufo_fi.domain.follow.service.FollowService;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class FollowController implements FollowApiSpec {
    private final FollowService followService;

    @Override
    public ResponseEntity<ResponseBody<FollowingCreateRes>> createFollow(
        Long followingId,
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.createFollow(followingId, defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<FollowerDeleteRes>> deleteFollow(
        Long followingId,
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.deleteFollower(followingId, defaultUserPrincipal.getId())));
    }
}
