package com.example.ufo_fi.domain.user.controller;


import com.example.ufo_fi.domain.user.controller.api.UserProfileApiSpec;
import com.example.ufo_fi.domain.user.dto.response.AnotherUserInfoReadRes;
import com.example.ufo_fi.domain.user.service.UserService;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserProfileController implements UserProfileApiSpec {

    private final UserService userService;


    @Override
    public ResponseEntity<ResponseBody<AnotherUserInfoReadRes>> readUser(Long anotherUserId,
        Long userId) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userService.readAnotherUser(anotherUserId, userId)
            )
        );
    }
}
