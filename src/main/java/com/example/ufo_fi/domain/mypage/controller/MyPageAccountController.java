package com.example.ufo_fi.domain.mypage.controller;

import com.example.ufo_fi.domain.mypage.controller.api.MyPageAccountApiSpec;
import com.example.ufo_fi.domain.user.dto.request.AccountCreateReq;
import com.example.ufo_fi.domain.user.dto.response.AccountCreateRes;
import com.example.ufo_fi.domain.user.dto.response.AccountReadRes;
import com.example.ufo_fi.domain.user.service.UserService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageAccountController implements MyPageAccountApiSpec {
    private final UserService userService;

    @Override
    public ResponseEntity<ResponseBody<AccountReadRes>> readAccount(
            Long userId
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.readUserAccount(userId)));
    }

    @Override
    public ResponseEntity<ResponseBody<AccountCreateRes>> createAccount(
            Long userId,
            AccountCreateReq accountCreateReq
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        userService.createUserAccount(userId, accountCreateReq)));
    }
}
