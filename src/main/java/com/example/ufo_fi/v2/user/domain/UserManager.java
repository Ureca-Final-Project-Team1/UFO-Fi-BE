package com.example.ufo_fi.v2.user.domain;

import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserInfoReq;
import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.infrastructure.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;

    public User validateUserExistence(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
    }

    public void validateUserRole(User user, Role role) {

        if(user.getRole() != role){
            throw new GlobalException(UserErrorCode.INVALID_ROLE);
        }
    }

    public User findById(Long userId) {

        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
    }

    public void updateUserNickname(
        User user, UserInfoReq userInfoReq, String randomNickname, ProfilePhoto randomProfilePhoto
    ) {
        user.signup(userInfoReq, randomNickname, randomProfilePhoto, true, Role.ROLE_USER);
    }

    public String getPhoneNumber(User user) {
        return user.getPhoneNumber() != null ? user.getPhoneNumber() : "";
    }

    public void updateUserNickname(User user, String nickname, Long userId) {
        user.updateNickname(nickname + String.format(" #%03d", userId));
    }
}
