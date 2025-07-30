package com.example.ufo_fi.v2.user.application;

import com.example.ufo_fi.v2.user.presentation.dto.response.UserRoleReadRes;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserManager userManager;
    private final UserMapper userMapper;

    public UserRoleReadRes readUserInfo(Long userId, Role role) {
        User user = userManager.findById(userId);
        String userPhoneNumber = userManager.getPhoneNumber(user);
        return userMapper.toUserRoleReadRes(role, userPhoneNumber);
    }
}
