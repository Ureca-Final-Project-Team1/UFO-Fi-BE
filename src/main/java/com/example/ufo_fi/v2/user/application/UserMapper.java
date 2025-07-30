package com.example.ufo_fi.v2.user.application;

import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.v2.user.domain.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserRoleReadRes toUserRoleReadRes(Role role, String userPhoneNumber) {
        return UserRoleReadRes.from(role, userPhoneNumber);
    }
}
