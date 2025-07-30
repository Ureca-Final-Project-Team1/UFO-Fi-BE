package com.example.ufo_fi.v2.user.presentation;

import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.global.security.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.user.application.UserService;
import com.example.ufo_fi.v2.user.presentation.api.UserApiSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApiSpec {

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseBody<UserRoleReadRes>> readUserInfo(
        DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
            ResponseBody.success(
                userService.readUserInfo(defaultUserPrincipal.getId(), defaultUserPrincipal.getRole())));
    }
}
