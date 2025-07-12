package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPageUserInfoApiSpec;
import com.example.ufo_fi.domain.user.dto.response.UserInfoReadRes;
import com.example.ufo_fi.domain.user.service.UserService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageUserInfoController implements MyPageUserInfoApiSpec {
    private final UserService UserService;

    @Override
    public ResponseEntity<ResponseBody<UserInfoReadRes>> readMyPageUserInfo(@RequestParam Long userId) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        UserService.readUser(userId)));
    }
}
