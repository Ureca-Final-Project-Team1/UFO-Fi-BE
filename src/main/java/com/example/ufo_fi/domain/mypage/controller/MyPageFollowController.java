package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.follow.dto.response.FollowersReadRes;
import com.example.ufo_fi.domain.follow.dto.response.FollowingsReadRes;
import com.example.ufo_fi.domain.follow.service.FollowService;
import com.example.ufo_fi.domain.mypage.controller.api.MyPageFollowApiSpec;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageFollowController implements MyPageFollowApiSpec {
    private final FollowService followService;

    @Override
    public ResponseEntity<ResponseBody<FollowingsReadRes>> readFollowings(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
            ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.readFollowings(defaultUserPrincipal.getId())));
    }

    @Override
    public ResponseEntity<ResponseBody<FollowersReadRes>> readFollowers(
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        followService.readFollowers(defaultUserPrincipal.getId())));
    }
}
