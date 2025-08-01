package com.example.ufo_fi.v2.user.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.global.security.oauth.provider.OAuth2Response;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserInfoReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "zet_asset")
    private Integer zetAsset;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "reputation")
    private String reputation;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_id")
    private Refresh refresh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_photo_id")
    private ProfilePhoto profilePhoto;

    public static User of(OAuth2Response oAuth2Response, Role role, Integer zetAsset) {
        return User.builder()
            .kakaoId(oAuth2Response.getProviderId().toString())
            .role(role)
            .email(oAuth2Response.getEmail())
            .zetAsset(zetAsset)
            .build();
    }

    public void registerRefresh(final Refresh refresh) {
        this.refresh = refresh;
    }

    public void purchasePosts(List<TradePost> posts) {

        int totalCost = posts.stream().mapToInt(TradePost::getTotalZet).sum();

        this.decreaseZetAsset(totalCost);
    }

    public void decreaseZetAsset(Integer totalZet) {

        if (this.zetAsset < totalZet) {
            throw new GlobalException(TradePostErrorCode.ZET_LACK);
        }
        this.zetAsset -= totalZet;
    }

    public void increaseZetAsset(Integer totalZet) {
        this.zetAsset += totalZet;
    }

    public void signup(
        UserInfoReq userInfoReq,
        String randomNickname,
        ProfilePhoto randomProfilePhoto,
        boolean activeStatus,
        Role roleUser
    ) {
        this.name = userInfoReq.getName();
        this.phoneNumber = userInfoReq.getPhoneNumber();
        this.nickname = randomNickname;
        this.profilePhoto = randomProfilePhoto;
        this.isActive = activeStatus;
        this.role = roleUser;
    }

    public void deleteRefresh() {
        this.refresh = null;
    }

    public void updateNickname(String targetNickname) {
        this.nickname = targetNickname;
    }

    public void updateStatusReported() {
        this.role = Role.ROLE_REPORTED;
    }

    public void updateRoleUser() {
        this.role = Role.ROLE_USER;
    }
}
