package com.example.ufo_fi.domain.user.entity;

import com.example.ufo_fi.domain.profilephoto.entity.ProfilePhoto;
import com.example.ufo_fi.domain.signup.dto.request.UserInfoReq;
import com.example.ufo_fi.domain.userplan.entity.UserPlan;
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
import jakarta.validation.constraints.Min;
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

    @Min(0)
    @Column(name = "zet_asset")
    private Integer zetAsset;

    @Column(name = "is_active")
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_photo_id")
    private ProfilePhoto profilePhoto;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_plan_id")
    private UserPlan userPlan;

    public void signup(UserInfoReq userInfoReq,
                       String randomNickname,
                       ProfilePhoto randomProfilePhoto,
                       boolean activeStatus,
                       Role roleUser) {
        this.name = userInfoReq.getName();
        this.phoneNumber = userInfoReq.getPhoneNumber();
        this.nickname = randomNickname;
        this.profilePhoto = randomProfilePhoto;
        this.isActive = activeStatus;
        this.role = roleUser;
    }

    public void registerUserPlan(UserPlan userPlan){
        this.userPlan = userPlan;
    }
}
