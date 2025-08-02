package com.example.ufo_fi.v2.user.domain;

import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserInfoReq;
import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.infrastructure.UserRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public void updateUserRole(User user, Role role) {
        user.updateRole(role);
    }

    public Page<User> findAllByRole(Role role, PageRequest pageRequest) {

        return userRepository.findAllByRole(role, pageRequest);
    }

    public User findByKakaoId(String string) {
        return userRepository.findByKakaoId(string);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public void decreaseZetAsset(User buyer, Integer totalZet) {
        buyer.decreaseZetAsset(totalZet);
    }

    public void increaseZetAsset(User seller, Integer totalZet) {
        seller.increaseZetAsset(totalZet);
    }
}
